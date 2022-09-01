package com.opencourse.path.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.path.entities.SubscriptionType;

@Repository
public interface SubTypeRepo extends MongoRepository<SubscriptionType,String>{
    
}
