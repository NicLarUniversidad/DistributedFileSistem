package com.ar.sdypp.distributed_file_system.file_manager.repositories.amqp;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileModel;
import com.ar.sdypp.distributed_file_system.file_manager.services.CipherService;
import com.ar.sdypp.distributed_file_system.file_manager.services.StorageService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.rabbitmq.client.*;
import org.jasypt.util.text.StrongTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;


//Se arma este código basándose en https://www.rabbitmq.com/tutorials/tutorial-one-java.html
//Hay otra implementación específica para Spring-boot https://spring.io/guides/gs/messaging-rabbitmq/
@Component
public class RabbitmqRepository {

    public final static Logger logger = LoggerFactory.getLogger(RabbitmqRepository.class);

    private Connection connection;
    private Channel channel;
    //Se asigna el valor por variable del programa, en el application.properties
    @Value("${sdypp.rabbitmq.host}")
    private String host;
    @Value("${sdypp.rabbitmq.queue.name}")
    private String queueName;
    //Cantidad máxima de mensajes que los consumidores pueden consumir a la vez
    private int prefetchCount = 1;
    @Value("${sdypp.rabbitmq.queue.exchange-name}")
    private String exchangeName;
    @Value("${sdypp.rabbitmq.queue.topics}")
    private String finishedWorkTopic;
    @Value("${sdypp.rabbitmq.queue.username}")
    private String username;
    @Value("${sdypp.rabbitmq.queue.password}")
    private String password;

    private final StorageService storageService;
    private final CipherService cipherService;

    @Autowired
    public RabbitmqRepository(Environment env, StorageService storageService, CipherService cipherService) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this.storageService = storageService;
        this.cipherService = cipherService;
        ConnectionFactory factory = new ConnectionFactory();
        this.queueName = "GuardadoArchivo"; //env.getProperty("sdypp.rabbitmq.queue.name");
        this.exchangeName = "default-exchange"; //env.getProperty("sdypp.rabbitmq.queue.exchange-name");
        this.finishedWorkTopic = "finished-works"; //env.getProperty("sdypp.rabbitmq.queue.topics");
        //this.prefetchCount = Integer.parseInt(env.getProperty("sdypp.rabbitmq.consumer.prefetch-count"));
        String uri = env.getProperty("sdypp.rabbitmq.uri");
        if (uri != null) {
            logger.info("Contectando con RabbitMQ... URI: " + uri);
            factory.setUri(uri);
        }
        else {
            this.host = env.getProperty("sdypp.rabbitmq.host");
            this.username = env.getProperty("sdypp.rabbitmq.queue.username");
            this.password = env.getProperty("sdypp.rabbitmq.queue.password");
            logger.info("Contectando con RabbitMQ... Host: " + this.host);
            factory.setHost(host);
            factory.setUsername(username);
            factory.setPassword(password);
        };
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.basicQos(prefetchCount);// channel.basicQos(prefetchCount);
        //Se agrega esto para poder usar tópicos
        channel.exchangeDeclare(exchangeName, "topic");
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

    public void listenQueue(String queue) throws IOException {
        try {

            logger.info("Contectando con RabbitMQ... Host: " + this.host);
            Channel channel = connection.createChannel();
            logger.info("Declarando cola...");
            channel.queueDeclare(queue, true, false, false, null);

            logger.info("Bind...");
            channel.queueBind(queueName, exchangeName, finishedWorkTopic);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8").trim();
                //Acá se debe procesar los mensajes recibidos
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new StringReader(message));
                reader.setLenient(true);
                FileModel file = gson.fromJson(reader, FileModel.class);
                logger.info("Message received: Content size: [{}]", file.getContent().length);
                try {
                    byte[] encryptData = this.cipherService.encrypt(file.getContent());
                    logger.info("Mensaje encriptado: " + new String(encryptData));
                    switch (file.getMessageType()) {
                        case FileModel.GUARDADO:
                            String fileUrl = this.storageService.saveFileOnBuckets(encryptData, file.getName());
                            logger.info("Se guardó el archivo en: {}", fileUrl);
                            break;
                        case FileModel.MODIFICACION:
                            this.storageService.update(file.getName(), encryptData);
                            logger.info("Se modificó el archivo: {}", file.getName());
                            break;
                        default:
                            logger.warn("Se reicibió un mensaje con tipo incorrecto, mensaje: {}", message);
                    }
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } catch (Exception e) {
                    logger.error("Error al cifrar el archivo", e);
                }
            };
            logger.info("Basic consume...");
            channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
