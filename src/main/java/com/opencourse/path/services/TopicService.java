package com.opencourse.path.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencourse.path.dtos.TopicDto;
import com.opencourse.path.dtos.mappers.TopicMapper;
import com.opencourse.path.entities.Topic;
import com.opencourse.path.exceptions.TopicNotFoundException;
import com.opencourse.path.repos.TopicRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TopicService {
    
    private final TopicRepo topicRepo;
    private final TopicMapper tMapper;

    
    //CRUD TOPIC
    //only admin
    public String addTopic(TopicDto dto){
        Topic topic=tMapper.toTopic(dto);
        topicRepo.save(topic);
        return topic.getId();
    }

    //all users
    public List<TopicDto> getAllTopics(){
        return topicRepo.findAll()
        .stream()
        .map((topic)->tMapper.toDto(topic))
        .collect(Collectors.toList());
    }

    //only admin
    public void updateTopic(TopicDto dto){

        //make sure topic exists
        Topic topic=topicRepo.findById(dto.getId())
        .orElseThrow(()->new TopicNotFoundException(dto.getId()));
        
        //update topic 
        topic.setName(dto.getName());
        topicRepo.save(topic);
    }

}
