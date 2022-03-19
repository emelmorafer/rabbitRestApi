package com.example.rabitrestapi.service.impl;


import com.example.rabitrestapi.configuration.RabbitMqConfig;
import com.example.rabitrestapi.configuration.model.RabbitMqConfigModel;
import com.example.rabitrestapi.rabbit.RabbitMqSender;
import com.example.rabitrestapi.service.TopicRabbitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TopicRabbitServiceImpl implements TopicRabbitService {
    private final RabbitAdmin rabbitAdmin;
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    private final RabbitMqConfig rabbitMqConfig;
    private final RabbitMqSender rabbitMqSender;

    public TopicRabbitServiceImpl(
            RabbitAdmin rabbitAdmin,
            RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry,
            RabbitMqConfig rabbitMqConfig,
            RabbitMqSender rabbitMqSender) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitListenerEndpointRegistry = rabbitListenerEndpointRegistry;
        this.rabbitMqConfig = rabbitMqConfig;
        this.rabbitMqSender = rabbitMqSender;
    }

    private static final Logger LOGGER = LogManager.getLogger(TopicRabbitServiceImpl.class);


    public String createQueue(String routingKey,String queueName) {
        try {
            /*Queue queue = new Queue(queueName, true, false, false);
            Binding binding = new Binding(
                    queueName,
                    Binding.DestinationType.QUEUE,
                    rabbitMqConfigModel.getExchange(),
                    routingKey,
                    null
            );*/

            Queue queue = new Queue(queueName, true, false, false);

            Map<String, Object> properties = new HashMap<>();
            properties.put("queuename", queueName);

            Binding binding = BindingBuilder.bind(queue).to(rabbitMqConfig.defaultExchange()).whereAny(properties).match();

            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);
            this.addQueueToListener(rabbitMqConfig.defaultExchange().getName(),queueName);

            return " a new Queue was created with name '" + queueName + "'";
        } catch (Exception e) {
            return "Something went wrong:" + e.getMessage();
        }
    }

    public void addQueueToListener(String exchangeName,String queueName) {
        if (!checkQueueExistOnListener(exchangeName,queueName)) {
            this.getMessageListenerContainerById(exchangeName).addQueueNames(queueName);
        }
    }

    public Boolean checkQueueExistOnListener(String exchangeName, String queueName) {
        try {
            String[] queueNames = this.getMessageListenerContainerById(exchangeName).getQueueNames();
            if (queueNames.length > 0) {
                for (String name : queueNames) {
                    if (name.equals(queueName)) {
                        return Boolean.TRUE;
                    }
                }
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public AbstractMessageListenerContainer getMessageListenerContainerById(String exchangeName) {
        return (
                (AbstractMessageListenerContainer) this.rabbitListenerEndpointRegistry.getListenerContainer(exchangeName)
        );
    }

    public String sendTopicMessage(String routingKey, String message) {
        try {
            rabbitMqSender.sendToRabbitWithRoutingKey(routingKey,message);
            return " a new Message was send '" + message + "' with routingKey '" + routingKey + "'";
        } catch (Exception e) {
            return "Something went wrong:" + e.getMessage();
        }
    }

    public String sendHeaderMessage(String message, Map<String, Object> properties) {
        try {
            rabbitMqSender.sendToRabbitWithHeader(message, properties);
            return " a new Message was send '" + message + "'";
        } catch (Exception e) {
            return "Something went wrong:" + e.getMessage();
        }
    }

}
