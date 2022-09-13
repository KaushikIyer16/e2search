package com.knsi.e2search.request;

import lombok.Data;

import java.util.Map;

@Data
public class CreateDocumentRequest {
    private String type;
    private Map<String, String> data;
}
