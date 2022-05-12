package com.miladalemansour.api.repository;

import reactor.core.publisher.Mono;

public interface IRepository {

    public Mono<String> setKey(String key, String value);

    public Mono<String> findByKey(String key);

}
