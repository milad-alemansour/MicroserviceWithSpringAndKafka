package com.mladalemansour.stream;


import java.util.Objects;

public class MessageModel {

    private String id;
    private String endpoint;
    private String uniqueCount;
    private String responseHeader;

    public MessageModel(String id, String endpoint) {
        this.id = id;
        this.endpoint = endpoint;
        this.responseHeader = "";
        this.uniqueCount = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getUniqueCount() {
        return uniqueCount;
    }

    public void setUniqueCount(String uniqueCount) {
        this.uniqueCount = uniqueCount;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "id='" + id + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", uniqueCount='" + uniqueCount + '\'' +
                ", responseHeader='" + responseHeader + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageModel that = (MessageModel) o;
        return Objects.equals(id, that.id) && Objects.equals(endpoint, that.endpoint) && Objects.equals(uniqueCount, that.uniqueCount) && Objects.equals(responseHeader, that.responseHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endpoint, uniqueCount, responseHeader);
    }

}
