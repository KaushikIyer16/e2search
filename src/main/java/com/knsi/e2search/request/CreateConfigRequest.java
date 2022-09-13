package com.knsi.e2search.request;

import lombok.Data;

import java.util.Map;

@Data
public class CreateConfigRequest {
    private String type;
    private Map<String, DataType> config;
}
