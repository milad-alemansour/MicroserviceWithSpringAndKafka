package com.mladalemansour.stream.consumer;

public enum TopicsEnum {
    ENDPOINT("endpoint-topic"),

    MESSAGE("message-topic"),

    JOURNAL("journal-topic");

    private String topic;

    TopicsEnum(String topic){
        this.topic = topic;
    }

    public String getTopic(){
        return topic;
    }

}
