package com.opencourse.path.apis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencourse.path.dtos.TopicDto;
import com.opencourse.path.services.TopicService;

@WebMvcTest(TopicController.class)
public class TopicControllerTest {
    
    @Autowired
    MockMvc mvc;
    @MockBean
    TopicService service;

    private String baseUrl="/api/v1/path/topic";
    private ObjectMapper mapper;

    @BeforeEach
    public void init(){
        mapper=new ObjectMapper();
    }

    @Test
    @DisplayName("should add topic")
    public void addTopicTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("name", "web");
        String payload=mapper.writeValueAsString(body);
        mvc.perform(
            post(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(service).addTopic(any(TopicDto.class));
    }

    @Test
    @DisplayName("should return all topics")
    public void getAllTopicTest() throws Exception{
        when(service.getAllTopics()).thenReturn(List.of(new TopicDto()));
        mvc.perform(get(baseUrl))
        .andExpect(status().isOk());
        verify(service).getAllTopics();
    }

    @Test
    @DisplayName("should update topic")
    public void updateTopicTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("id", "dbr");
        body.put("name", "web");
        String payload=mapper.writeValueAsString(body);
        mvc.perform(
            put(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(service).updateTopic(any(TopicDto.class));
    }

}
