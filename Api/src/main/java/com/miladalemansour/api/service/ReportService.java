package com.miladalemansour.api.service;

import com.google.gson.Gson;
import com.miladalemansour.api.model.Message;
import com.miladalemansour.api.repository.LockStatus;
import com.miladalemansour.api.repository.RedisRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Service
public class ReportService {

    private static Logger LOGGER = Logger.getLogger(ReportService.class.getName());
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");

    private final RedisRepository redisRepository;

    private final MessageProducerService producerService;

    private final Gson gson;

    public ReportService(RedisRepository redisRepository, MessageProducerService producerService, Gson gson) {
        this.redisRepository = redisRepository;
        this.producerService = producerService;
        this.gson = gson;
    }

    public Mono<String> getUniqueCount(String key) {
        return redisRepository.getUniqueCount(key).defaultIfEmpty("0");
    }

    public Mono<String> getUniqueCountOfCurrentMinute() {

        return getUniqueCount(getCurrentMinute());
    }

    public void sendUniqueCountToStream() {
        //get report of last minute
        String timeFrame = getXMinuteAgo(1);

        //check Report Already sent or not, if not put lock on report
        redisRepository.isLock(timeFrame).flatMap(lock -> redisRepository.setLock(timeFrame).map(__ -> lock))
                .flatMap(lock -> {
                    // if report not locked then get count and send it to journal
                    if (lock.equals(LockStatus.UNLOCK.getStatus())) {
                        getUniqueCount(timeFrame).map(x -> (x.isEmpty()) ? "0" : x).flatMap(uniqueCount -> {
                            Message message = new Message("filepath://", uniqueCount, "");
                            producerService.sendEndpointToCallEndpointStream(gson.toJson(message));
                            return Mono.just(uniqueCount);
                        });
                        return Mono.just("Send Report to Stream");
                    }
                    return Mono.just("Report Already Sent");
                }).subscribe(LOGGER::info);
    }

    private String getCurrentMinute() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    private String getXMinuteAgo(long minuteAgo) {
        return LocalDateTime.now().minusMinutes(minuteAgo).format(dateTimeFormatter);
    }
}
