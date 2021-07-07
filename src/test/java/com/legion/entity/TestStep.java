package com.legion.entity;


import com.legion.enums.HttpType;
import com.legion.enums.HttpType;

import java.util.HashMap;
import java.util.Map;


public class TestStep {

    private HttpType type;

    private String path;

    private Map<String, Object> params = new HashMap<>();
    private Map<String, Object> header = new HashMap<>();

    private String body;

    public HttpType getType() {
        return type;
    }

    public void setType(HttpType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    public void setHeader(final Map<String, Object> header) {
        this.header = header;
    }
    public  Map<String, Object> getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
