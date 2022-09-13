package com.knsi.e2search.configuration;

import com.knsi.e2search.request.DataType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class E2Configuration {

    @Bean(name = "configBean")
    public Map<String, Map<String, DataType>> configBean(){
        return new HashMap<>();
    }
}
