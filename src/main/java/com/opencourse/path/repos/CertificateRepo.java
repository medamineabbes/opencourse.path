package com.opencourse.path.repos;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.opencourse.path.entities.Certificate;

@Repository
public interface CertificateRepo extends MongoRepository<Certificate,String>{
    Optional<Certificate> findByUserIdAndPathId(Long userId,String pathId);
}
