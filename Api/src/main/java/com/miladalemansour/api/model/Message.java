package com.miladalemansour.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {

    private String endpoint;
    private String uniqueCount;
    private String responseHeader;

    public Message(Message message) {
        this.endpoint = message.getEndpoint();
        this.uniqueCount = message.getUniqueCount();
        this.responseHeader = message.getResponseHeader();
    }
}
