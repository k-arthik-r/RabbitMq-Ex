package com.voidex.rabbitMqDirect;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import static com.voidex.rabbitMqDirect.DirectConstants.EXCHANGE;
import static com.voidex.rabbitMqDirect.DirectConstants.ROUTING_KEY;
import static com.voidex.rabbitMqDirect.DirectConstants.DIRECT;

public class DirectConsumer {
    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE, DIRECT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, EXCHANGE, ROUTING_KEY);

        System.out.println(" [*] Waiting for messages with key '" + ROUTING_KEY + "'...");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(queue, true, callback, consumerTag -> {});

    }
}   