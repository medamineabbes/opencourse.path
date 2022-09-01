package com.opencourse.path.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.path.entities.OverviewElement;

@Repository
public interface ElementRepo extends MongoRepository<OverviewElement,String>{
    
}
