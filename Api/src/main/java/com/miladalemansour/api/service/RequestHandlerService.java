package com.miladalemansour.api.service;

import com.google.gson.Gson;
import com.miladalemansour.api.model.AcceptModel;
import com.miladalemansour.api.model.Message;
import com.miladalemansour.api.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;


@Service
public class RequestHandlerService {
    private static Logger LOGGER = Logger.getLogger(RequestHandlerService.class.getName());
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private Gson gson;

    @Autowired
    MessageProducerService messageProducerService;

    public Mono<String> saveIncomingId(AcceptModel request) {

        redisRepository.findById(request.getId())
                .flatMap(x -> !x.equals("0")?Mono.just(x):redisRepository.incrementCountUnique(getCurrentMinute()))
                .flatMap(x -> redisRepository.setId(request.getId(), String.valueOf(Integer.parseInt(x) + 1)))
                .subscribe(LOGGER::info);
        return Mono.just("ok");
        // count each id repeated totally

    }

    private String getCurrentMinute() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }


}
