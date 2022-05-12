package com.mladalemansour.stream.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

@Service
public class CallEndpointService {
    private static final Logger LOGGER = Logger.getLogger(CallEndpointService.class.getName());
    HttpClient client = HttpClient.newHttpClient();

    public  void callEndpoint(String endpoint) {
        // Start Calling endpoint ASYNC
        LOGGER.info("Call endpoint="+endpoint);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(pageResponse ->
                        LOGGER.info("endpoint="+endpoint + ", response_status_code=" + pageResponse.statusCode()))
                .exceptionally(ex -> {
                    LOGGER.info("endpoint="+endpoint+ ", \" recovered_from=" + ex.getMessage() + "\"");
                    return null;})
                .join();
    }
}
