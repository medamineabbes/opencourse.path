package com.opencourse.path.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TopicDto {
    
    private String id;
    
    @NotBlank(message = "name is mandatory")
    @NotEmpty(message = "name is mandatory")
    private String name;
    
}
