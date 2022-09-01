package com.opencourse.path.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.path.entities.Topic;

@Repository
public interface TopicRepo extends MongoRepository<Topic,String>{
    
}
