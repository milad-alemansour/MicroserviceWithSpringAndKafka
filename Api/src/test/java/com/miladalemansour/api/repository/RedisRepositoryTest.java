package com.miladalemansour.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;


@SpringBootTest
class RedisRepositoryTest {

    @Autowired
    private RedisRepository redisRepository;

    @Test
    void setKey() {
        StepVerifier.create(redisRepository.setKey("id","1").flatMap(__-> redisRepository.findByKey("id")))
                .expectNext("1")
                .verifyComplete();


    }

    @Test
    void findByKey() {
        StepVerifier.create(redisRepository.findByKey("id"))
                .expectNext("1")
                .verifyComplete();
    }
}