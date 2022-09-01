package com.opencourse.path.entities;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("topic")
public class Topic {
    
    @Id
    private String id;
    
    @NotBlank(message = "name is mandatory")
    @NotEmpty(message = "name is mandatory")
    private String name;

    
    //paths
    @DBRef
    private List<Path> paths;

}
