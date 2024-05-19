package ar.com.unlu.sdypp.integrador.file.manager.repositories.amqp;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


//Se arma este código basándose en https://www.rabbitmq.com/tutorials/tutorial-one-java.html
//Hay otra implementación específica para Spring-boot https://spring.io/guides/gs/messaging-rabbitmq/
@Component
public class RabbitmqRepository {

    private final Connection connection;
    private final Channel channel;
    //Se asigna el valor por variable del programa, en el application.properties
    @Value("${sdypp.rabbitmq.host:localhost}")
    private String host = "localhost";
    @Value("${sdypp.rabbitmq.queue.name:default}")
    private String queueName = "default";
    @Value("${sdypp.rabbitmq.queue.username:guest}")
    private String username = "guest";
    @Value("${sdypp.rabbitmq.queue.password:123}")
    private String password = "123";
    //Cantidad máxima de mensajes que los consumidores pueden consumir a la vez
    @Value("${sdypp.rabbitmq.consumer.prefetch-count:1}")
    private int prefetchCount = 1;
    @Value("${sdypp.rabbitmq.queue.exchange-name:default-exchange}")
    private String exchangeName = "default-exchange";
    @Value("${sdypp.rabbitmq.queue.topics.finish-work:works.finished}")
    private String finishedWorkTopic = "works.finished";

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
    }

    //Investigar más cómo integrar con RPC https://www.rabbitmq.com/tutorials/tutorial-six-java.html
    public void send(String message) throws IOException {
        channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    public void listenQueue(String queue) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, false, false, false, null);

        channel.queueBind(queueName, exchangeName, finishedWorkTopic);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            //Acá se debe procesar los mensajes recibidos
            System.out.println(message);
            //Se avisa que se procesó el mensaje
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });
    }
}
