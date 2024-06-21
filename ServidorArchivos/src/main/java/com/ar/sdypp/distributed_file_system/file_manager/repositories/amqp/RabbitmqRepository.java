package com.ar.sdypp.distributed_file_system.file_manager.repositories.amqp;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileModel;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.apache.logging.log4j.message.Message;
import org.jasypt.util.text.StrongTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;


//Se arma este código basándose en https://www.rabbitmq.com/tutorials/tutorial-one-java.html
//Hay otra implementación específica para Spring-boot https://spring.io/guides/gs/messaging-rabbitmq/
@Component
public class RabbitmqRepository {

    public final static Logger logger = LoggerFactory.getLogger(RabbitmqRepository.class);

    private final Connection connection;
    private final Channel channel;
    //Se asigna el valor por variable del programa, en el application.properties
    @Value("${sdypp.rabbitmq.host:localhost}")
    private String host = "localhost";
    @Value("${sdypp.rabbitmq.queue.name:GuardadoArchivo}")
    private String queueName = "GuardadoArchivo";
    //Cantidad máxima de mensajes que los consumidores pueden consumir a la vez
    @Value("${sdypp.rabbitmq.consumer.prefetch-count:1}")
    private int prefetchCount = 1;
    @Value("${sdypp.rabbitmq.queue.exchange-name:default-exchange}")
    private String exchangeName = "default-exchange";
    @Value("${sdypp.rabbitmq.queue.topics.finish-work:works.finished}")
    private String finishedWorkTopic = "works.finished";
    @Value("${sdypp.rabbitmq.queue.username:guest}")
    private String username = "guest";
    @Value("${sdypp.rabbitmq.queue.password:123}")
    private String password = "123";
    private final StrongTextEncryptor textEncryptor;

    public RabbitmqRepository() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.basicQos(prefetchCount);
        //Se agrega esto para poder usar tópicos
        channel.exchangeDeclare(exchangeName, "topic");
        this.textEncryptor = new StrongTextEncryptor();
        this.textEncryptor.setPassword("ultrasecreta");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("!");
                    listenQueue(queueName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
    }

    //Investigar más cómo integrar con RPC https://www.rabbitmq.com/tutorials/tutorial-six-java.html
    public void send(String message) throws IOException {
        channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    public void listenQueue(String queue) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, false, null);

        channel.queueBind(queueName, exchangeName, finishedWorkTopic);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("Message received: " + message);
            //Acá se debe procesar los mensajes recibidos
            Gson gson = new Gson();
            FileModel file = gson.fromJson(message, FileModel.class);
            String path = file.getUsername().replace(":", "/").replace(".", "/");
            File filePath = new File(path);
            //filePath.exists();
            Files.createDirectories(Paths.get(username.replace(":", "/").replace(".", "/")));
            File newFIle = new File(filePath, file.getName());
            logger.info(newFIle.getAbsolutePath());
            if (!newFIle.exists()) {
                newFIle.createNewFile();
            }
            var encryptData = textEncryptor.encrypt(file.getContent()).getBytes();
            Files.write(newFIle.toPath(), encryptData);
            //Se avisa que se procesó el mensaje
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            //TODO: ACK que se guardó el archivo
            //this.send(); objeto nuevo que comunique donde se guardó la parte, con un id y path
            //Debe estar en un tópico separado
        };
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
    }
}
