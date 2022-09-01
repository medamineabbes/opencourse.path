package com.opencourse.path.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Subscription {
    
    @Id
    private String id;
    private Long userId;
    private LocalDateTime date;

    //type
    @DBRef
    private SubscriptionType type;

    //path
    @DBRef
    private Path path;
}
