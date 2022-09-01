package com.opencourse.path.dtos.mappers;

import org.springframework.stereotype.Component;

import com.opencourse.path.dtos.TopicDto;
import com.opencourse.path.entities.Topic;

@Component
public class TopicMapper {
    

    public Topic toTopic(TopicDto dto){
        Topic topic=new Topic();
        topic.setId(dto.getId());
        topic.setName(dto.getName());
        return topic;
    }

    public TopicDto toDto(Topic topic){
        TopicDto dto=new TopicDto();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        return dto;
    }
}
