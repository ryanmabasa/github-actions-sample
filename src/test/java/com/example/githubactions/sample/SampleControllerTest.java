package com.example.githubactions.sample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SampleService service;


    @Test
    void it_should_save() throws Exception {
        doNothing().when(service).sampleSave(any(SampleDto.class));
        this.mockMvc.perform(post("/samples")
                .content("{\n" +
                    "    \"value\":\"test\"\n" +
                    "}")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print()).andExpect(status().isCreated());
//            .andExpect(content().json("{\n" +
//                "    \"flag\":true\n" +
//                "}"));
        verify(service,times(1)).sampleSave(any(SampleDto.class));
    }



}