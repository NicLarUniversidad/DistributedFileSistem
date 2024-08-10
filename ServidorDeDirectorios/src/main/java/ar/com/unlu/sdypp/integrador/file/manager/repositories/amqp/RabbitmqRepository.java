package ar.com.unlu.sdypp.integrador.file.manager.repositories.amqp;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;


//Se arma este código basándose en https://www.rabbitmq.com/tutorials/tutorial-one-java.html
//Hay otra implementación específica para Spring-boot https://spring.io/guides/gs/messaging-rabbitmq/
@Component
public class RabbitmqRepository {

    public final static Logger logger = LoggerFactory.getLogger(RabbitmqRepository.class);

    private final Connection connection;
    private final Channel channel;
    //Se asigna el valor por variable del programa, en el application.properties
    @Value("${sdypp.rabbitmq.host}")
    private String host;
    @Value("${sdypp.rabbitmq.queue.name}")
    private String queueName;
    //Cantidad máxima de mensajes que los consumidores pueden consumir a la vez
    //@Value("${sdypp.rabbitmq.consumer.prefetch-count:1}")
    private int prefetchCount = 1;
    @Value("${sdypp.rabbitmq.queue.exchange-name}")
    private String exchangeName;
    @Value("${sdypp.rabbitmq.queue.topics}")
    private String finishedWorkTopic;
    @Value("${sdypp.rabbitmq.queue.username}")
    private String username;
    @Value("${sdypp.rabbitmq.queue.password}")
    private String password;

    @Autowired
    public RabbitmqRepository(Environment env) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
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
