package com.example.rabitrestapi.controller;

import com.example.rabitrestapi.service.TopicRabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("topicrabbit")
public class TopicRabbitController {

    @Autowired
    TopicRabbitService topicRabbitService;


    @PostMapping(value = "/createQueue")
    public String createQueue(
            @RequestParam(value = "routingKey") String routingKey,
            @RequestParam(value = "queueName") String queueName) {

        return topicRabbitService.createQueue(routingKey,queueName);
    }

    @PostMapping(value = "/sendMessage")
    public String sendMessage(
            @RequestParam(value = "routingKey") String routingKey,
            @RequestParam(value = "message") String message) {

        return topicRabbitService.sendTopicMessage(routingKey, message);
    }

    @PostMapping(value = "/sendMessageHeader")
    public String sendMessage(
            @RequestHeader Map<String, Object> properties,
            @RequestParam(value = "message") String message) {

        return topicRabbitService.sendHeaderMessage(message,properties);
    }


}
