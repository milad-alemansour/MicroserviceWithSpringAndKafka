package com.miladalemansour.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MessageProducerService {
    private static final String JOURNAL_TOPIC = "journal-topic";
    private static final String ACCEPT_TOPIC = "accept-topic";
    private static final String ENDPOINT_TOPIC = "endpoint-topic";
    private static final Logger LOGGER = Logger.getLogger(MessageProducerService.class.getName());
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendAcceptedRequestToStream(String message) {
        kafkaTemplate.send(ACCEPT_TOPIC, message);
    }
    public void sendEndpointToCallEndpointStream(String message) {
        kafkaTemplate.send(ENDPOINT_TOPIC, message);
    }

    public void journal(String message){
        LOGGER.info("Kafka Result");
        LOGGER.info(kafkaTemplate.send(JOURNAL_TOPIC, message).toString());
    }
}
