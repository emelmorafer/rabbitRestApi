package com.example.rabitrestapi;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class TopicRabbitApplication {

	//static Channel channel;

	public static void main(String[] args) throws IOException, TimeoutException {
		SpringApplication.run(TopicRabbitApplication.class, args);
		//channel = conectToRabbit();
	}

	/*public static Channel getChannel() {
		return channel;
	}*/

	/*public static Channel conectToRabbit() throws IOException, TimeoutException {
		// carretera = coneccion    cual carretera
		// por donde vamos a transitar o que calzada = channel   por dondeme meto
		// de que manera vamos a transitar = exvhange  como transito

		// Create a factory
		ConnectionFactory factory = new ConnectionFactory();

		// Setting parameters
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		//factory.setVirtualHost("brahma");

		// Create a connection
		Connection connection = factory.newConnection();

		// Create pipeline
		Channel channel = connection.createChannel();

		// Create a switch
		String  exchangeName = "vishnu";
		channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);

		return channel;
	}*/

}
