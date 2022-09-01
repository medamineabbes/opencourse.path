package com.opencourse.path.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencourse.path.dtos.TopicDto;
import com.opencourse.path.services.TopicService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/path/topic")
@AllArgsConstructor
public class TopicController {
    
    private final TopicService service;

    //only admin
    @PostMapping
    public ResponseEntity<String> addTopic(@RequestBody(required = true) TopicDto dto){
        String id=service.addTopic(dto);
        return ResponseEntity.ok(id);
    }

    //all users
    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics(){
        List<TopicDto> topics=service.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    //only admin
    @PutMapping
    public ResponseEntity<Object> updateTopic(@RequestBody(required = true) TopicDto dto){
        service.updateTopic(dto);
        return ResponseEntity.ok().build();
    }

    
}
