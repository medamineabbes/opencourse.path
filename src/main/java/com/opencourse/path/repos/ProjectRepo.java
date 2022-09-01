package com.opencourse.path.repos;

import com.opencourse.path.entities.Project;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends MongoRepository<Project,String>{
    
}
