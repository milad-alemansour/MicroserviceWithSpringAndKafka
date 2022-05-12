package com.mladalemansour.stream.consumer;


import com.google.gson.Gson;

import com.mladalemansour.stream.MessageModel;
import com.mladalemansour.stream.service.CallEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.logging.Logger;

@Component
public class EndpointConsumer {

    private static final String TOPIC = "endpoint-topic";
    private final static Logger LOGGER = Logger.getLogger(EndpointConsumer.class.getName());
    ExecutorService executorService;

    @Autowired
    private CallEndpointService callEndpointService;

    @Autowired
    private Gson gson;

    public EndpointConsumer() {
        this.executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

    }

    @KafkaListener(topics = TOPIC, groupId = "group-id")
    public void consume(String message) {
        LOGGER.info("Message Received: topic=" + TOPIC + ", message=" + message);

        MessageModel messageModel = gson.fromJson(message, MessageModel.class);
        // consume endpoints provided by api microservice or any other microservices parallel
        executorService.submit(() -> {
            callEndpointService.callEndpoint(messageModel.getEndpoint());
        });

    }


}
