package com.opencourse.path.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.opencourse.path.dtos.TopicDto;
import com.opencourse.path.dtos.mappers.TopicMapper;
import com.opencourse.path.entities.Topic;
import com.opencourse.path.exceptions.TopicNotFoundException;
import com.opencourse.path.repos.TopicRepo;

public class TopicServiceTest {
    
    private TopicRepo topicRepo=mock(TopicRepo.class);
    private TopicMapper tMapper=new TopicMapper();
    private TopicService service;

    private Topic web;

    @BeforeEach
    public void init(){
        service=new TopicService(topicRepo,tMapper);
        web=new Topic();
        web.setId("id");
        web.setName("web");
    }

    @Test
    @DisplayName("should add topic")
    public void addTopicTest(){
        TopicDto dto=new TopicDto();
        dto.setName("web");
        service.addTopic(dto);
        verify(topicRepo).save(any(Topic.class));
    } 

    @Test
    @DisplayName("should get all topics")
    public void getAllTopicsTest(){
        when(topicRepo.findAll()).thenReturn(List.of(web));
        List<TopicDto> returned=service.getAllTopics();
        assertEquals(1,returned.size());
        assertEquals(web.getName(),returned.get(0).getName());
    }

    @Test
    @DisplayName("should throw topic not found exception")
    public void updateTopicTest(){
        TopicDto dto=new TopicDto();
        dto.setId("id");
        dto.setName("web");
        assertThrows(TopicNotFoundException.class, ()->{
            service.updateTopic(dto);
        });
    }

    @Test
    @DisplayName("should update topic")
    public void updateTopicTest2(){
        TopicDto dto=new TopicDto();
        dto.setName("ddd");
        dto.setId("id t1");

        when(topicRepo.findById("id t1")).thenReturn(Optional.of(web));

        service.updateTopic(dto);
        verify(topicRepo).save(any(Topic.class));
    }
}
