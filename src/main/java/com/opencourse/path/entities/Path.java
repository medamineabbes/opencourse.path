package com.opencourse.path.entities;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("path")
public class Path {
    
    @Id
    private String id;

    @NotBlank(message = "title is mandatory")
    @NotEmpty(message = "title is mandatory")
    private String title;

    @NotBlank(message = "description is mandatory")
    @NotEmpty(message = "descirption is mandatory")
    private String description;
    
    @NotBlank(message = "image is mandatory")
    @NotEmpty(message = "image is mandatory")
    private String image;

    @NotNull(message = "duration is mandatory")
    private int durationMonth;

    private Boolean active;
    private Long creatorId;
    
    //topic
    @DBRef
    private Topic topic;

    //projects
    @DBRef
    private List<Project> projects;

    //elments
    @DBRef
    private List<OverviewElement> elements;

    //subscriptions
    @DBRef
    private List<Subscription> subscription;

    //certificates
    @DBRef
    private List<Certificate> certificates;
}
