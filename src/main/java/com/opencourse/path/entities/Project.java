package com.opencourse.path.entities;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Project {
    
    @Id
    private String id;

    @NotBlank(message = "title is mandatory")
    @NotEmpty(message = "title is mandatory")
    private String title;

    @NotBlank(message = "description is mandatory")
    @NotEmpty(message = "description is mandatory")
    private String description;

    @NotNull(message = "duration is mandatory")
    private int durationHour;

    //path
    @DBRef
    private Path path;

    //courses
    @Size(min=2,message="project must have at least 2 courses")
    private List<Long> courseIds;

    //finished by user
    private List<FinishedProject> finished;
}
