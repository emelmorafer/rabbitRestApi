package com.example.rabitrestapi.service;


import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;

import java.util.Map;

public interface TopicRabbitService {

    String createQueue(String routingKey,String queueName);

    String sendTopicMessage (String routingKey, String message);

    void addQueueToListener(String exchangeName,String queueName);

    Boolean checkQueueExistOnListener(String exchangeName, String queueName);

    AbstractMessageListenerContainer getMessageListenerContainerById(String exchangeName);

    String sendHeaderMessage(String message, Map<String, Object> properties);

}
