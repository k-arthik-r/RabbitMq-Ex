package com.voidex.rabbitMqDirect;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import static com.voidex.rabbitMqDirect.DirectConstants.EXCHANGE;
import static com.voidex.rabbitMqDirect.DirectConstants.ROUTING_KEY;
import static com.voidex.rabbitMqDirect.DirectConstants.DIRECT;

import java.util.Scanner;


public class DirectProducer {
    public static void main(String[] args) throws Exception{
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            Scanner scanner = new Scanner(System.in);){

            channel.exchangeDeclare(EXCHANGE, DIRECT);
            System.out.println("Enter the message: ");
            String message = scanner.nextLine();
            channel.basicPublish(EXCHANGE, ROUTING_KEY, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + ROUTING_KEY + "':'" + message + "'");
        }

    }
}
