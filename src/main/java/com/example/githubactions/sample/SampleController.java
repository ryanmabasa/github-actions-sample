package com.example.githubactions.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @PostMapping("/samples")
    @ResponseStatus(HttpStatus.CREATED)
    public void sampleSave(@RequestBody SampleDto sampleDto){
        sampleService.sampleSave(sampleDto);
    }
}
