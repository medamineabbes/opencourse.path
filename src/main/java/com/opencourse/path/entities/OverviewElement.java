package com.opencourse.path.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class OverviewElement {
    
    @Id
    private String id;

    @NotBlank(message = "title is mandatory")
    @NotEmpty(message = "title is mandatory")
    private String title;

    @Size(min = 100,message = "content min size is 100 characteres")
    private String content;

    //path
    @DBRef
    private Path path;
}
