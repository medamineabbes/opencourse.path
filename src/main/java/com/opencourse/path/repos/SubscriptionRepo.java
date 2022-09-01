package com.opencourse.path.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.path.entities.Subscription;

@Repository
public interface SubscriptionRepo extends MongoRepository<Subscription,String>{
    List<Subscription> findByUserId(Long userId);
}
