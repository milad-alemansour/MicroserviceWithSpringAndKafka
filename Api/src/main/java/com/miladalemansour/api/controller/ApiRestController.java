package com.miladalemansour.api.controller;

import com.google.gson.Gson;
import com.miladalemansour.api.model.AcceptModel;
import com.miladalemansour.api.model.Message;
import com.miladalemansour.api.service.MessageProducerService;
import com.miladalemansour.api.service.ReportService;
import com.miladalemansour.api.service.RequestHandlerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/smatoo")
public class ApiRestController {
    public static Logger LOGGER = Logger.getLogger(ApiRestController.class.getName());

    private final Gson gson;
    @Value("${server.port}")
    private String port;

    private final RequestHandlerService requestHandlerService;
    private final ReportService reportService;
    private final MessageProducerService messageProducerService;

    public ApiRestController(RequestHandlerService requestHandlerService, MessageProducerService messageProducerService,
                             ReportService reportService, Gson gson) {
        this.requestHandlerService = requestHandlerService;
        this.messageProducerService = messageProducerService;
        this.reportService = reportService;
        this.gson = gson;
    }

    @GetMapping("/error")
    public Mono<ResponseEntity<String>> getDefaultPage(@RequestHeader(name = "errorcode") String errorCode) {
        LOGGER.info("error code : " + errorCode);
        return Mono.just(ResponseEntity.ok("failed"));
    }

    @GetMapping("/accept")
    public Mono<ResponseEntity<String>> accept(@RequestParam(name = "id", defaultValue = "0") String id,
                                               @RequestParam(name = "endpoint", defaultValue = "") String endpoint) {

        LOGGER.info("Response come in port: " + port + ", with Data:id=" + id + ", endpoint=" + endpoint);
        //save id and count
        AcceptModel request = new AcceptModel(id, endpoint);

        //Save Incoming ID
        requestHandlerService.saveIncomingId(request);
        //send endpoint to call and run
        messageProducerService.sendAcceptedRequestToStream(gson.toJson(request));

        // if endpoint not provided by user
        if (request.getEndpoint().isEmpty())
            return Mono.just(ResponseEntity.ok("ok"));

        // if endpoint provided send unique id of current minutes
        reportService.getUniqueCountOfCurrentMinute().flatMap(uniqueCount -> {
            Message message = new Message(request.getEndpoint(), uniqueCount, "");
            messageProducerService.sendEndpointToCallEndpointStream(gson.toJson(message));
            return Mono.just(uniqueCount);
        }).subscribe(LOGGER::info);

        return Mono.just(ResponseEntity.ok("ok"));

    }

}
