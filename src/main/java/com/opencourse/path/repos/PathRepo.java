package com.opencourse.path.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.path.entities.Path;

@Repository
public interface PathRepo extends MongoRepository<Path,String>{
    Page<Path> searchByTitleContainingAndActive(String title,boolean active,Pageable pageable);
}
