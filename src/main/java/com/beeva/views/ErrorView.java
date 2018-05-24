package com.beeva.views;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorView {

    private String code;
    private String message;

    public ErrorView() {

    }

    public ErrorView(String code, String errorMessage) {
        this.setCode(code);
        this.setMessage(errorMessage);
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
