package com.example.githubactions.sample;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class SampleRepo {

    private final Map<String,String> data = new HashMap<>();

    @PostConstruct
    public void postConstruct(){
        data.put("test","value");
    }

    public String save(String value){
       return data.put(UUID.randomUUID().toString(),value);
    }

    public String get(String key){
        return data.get(key);
    }
}
