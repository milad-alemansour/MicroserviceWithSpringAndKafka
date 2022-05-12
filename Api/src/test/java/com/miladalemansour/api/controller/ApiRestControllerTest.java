package com.miladalemansour.api.controller;

import com.miladalemansour.api.model.AcceptModel;
import com.miladalemansour.api.service.RequestHandlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@WebFluxTest(controllers = ApiRestController.class)
class ApiRestControllerTest {

/*    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RequestHandlerService requestHandlerService;

    @Test
    void accept() {
        webTestClient.get().uri("/api/smatoo/accept?id=1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }*/
}