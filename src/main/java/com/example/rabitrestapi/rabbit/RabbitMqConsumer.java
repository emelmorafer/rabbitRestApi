package com.example.rabitrestapi.rabbit;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Map;

@Component
@Log4j2
public class RabbitMqConsumer {

    @RabbitListener(id = "${props.rabbitmq.exchange}",concurrency = "2")
    public void receiver(Message message) {

        Map<String, Object> headers = message.getMessageProperties().getHeaders();

        String body = new String(message.getBody(), Charset.forName("utf8"));

        log.info("Message received from queue: {}, message : {}",headers.get("queuename").toString(),body);
    }

}
