package com.miladalemansour.api.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.logging.Logger;

@Repository
public class RedisRepository implements IRepository {

    private static final Logger LOGGER = Logger.getLogger(RedisRepository.class.getName());
    private static final String ID_PREFIX = "id:";
    private static final String UNIQUE_COUNTER_PREFIX = "unq-cnt:";
    private static final String LOCK_REPORT_PREFIX = "lck:";
    private final ReactiveRedisOperations<String, String> operations;

    public RedisRepository(@Qualifier("reactiveStringRedisTemplate") ReactiveRedisOperations<String, String> operations) {
        this.operations = operations;
    }

    @Override
    public Mono<String> setKey(String key, String value) {
        return operations.opsForValue().set(key, value).map(__ -> value);
    }

    public Mono<String> setId(String id, String value) {
        return setKey(ID_PREFIX + id, value);
    }

    @Override
    public Mono<String> findByKey(String key) {
        return operations.opsForValue().get(key)
                .map(x -> x);

    }

    public Mono<String> findById(String key) {
        return findByKey(ID_PREFIX + key).defaultIfEmpty("0");
    }

    public Mono<String> incrementCountUnique(String key) {

        return operations.opsForValue().increment(UNIQUE_COUNTER_PREFIX + key).map(String::valueOf);
    }


    public Mono<String> getUniqueCount(String key) {
        return findByKey(UNIQUE_COUNTER_PREFIX + key).defaultIfEmpty("0");
    }

    public Mono<String> isLock(String key) {
        return findByKey(LOCK_REPORT_PREFIX + key)
                .defaultIfEmpty(LockStatus.UNLOCK.getStatus());
    }

    public Mono<String> setLock(String key) {
        return setKey(LOCK_REPORT_PREFIX + key, LockStatus.LOCK.getStatus());
    }
}
