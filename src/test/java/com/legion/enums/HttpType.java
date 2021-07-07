package com.legion.enums;

public enum HttpType {
    POST("post"),
    GET("get");

    private String value;

    HttpType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
