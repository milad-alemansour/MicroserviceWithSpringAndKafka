package com.mladalemansour.stream.consumer;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageConsumer {
    private static final String TOPIC = "accept-topic";
    private static final Logger LOGGER = Logger.getLogger(MessageConsumer.class.getName());
    @Autowired
    private Gson gson;

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void
    consume(String message)
    {
        LOGGER.info("Message Received: topic=" + TOPIC + ", message=" + message);
        // in order to need add functionality to accepted requests
    }
}
