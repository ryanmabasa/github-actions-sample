package com.example.githubactions.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private SampleRepo sampleRepo;

    @Autowired
    public SampleService(SampleRepo sampleRepo) {
        this.sampleRepo = sampleRepo;
    }

    public void sampleSave(SampleDto sampleDto){
        sampleRepo.save(sampleDto.getValue());
    }

}
