package com.mladalemansour.stream.service;

import com.mladalemansour.stream.consumer.TopicsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MessageProducerService {
    private static final Logger LOGGER = Logger.getLogger(MessageProducerService.class.getName());
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void processAcceptModel(String message) {
        LOGGER.info("Kafka Result");
        LOGGER.info(kafkaTemplate.send(TopicsEnum.MESSAGE.getTopic(), message).toString());
    }
    public void sendCallEndpoint(String message) {
        LOGGER.info("Kafka Result");
        LOGGER.info(kafkaTemplate.send(TopicsEnum.ENDPOINT.getTopic(), message).toString());
    }
}
