package com.opencourse.path.dtos;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProjectDto {
    
    private String id;

    @NotBlank(message = "title is mandatory")
    @NotEmpty(message = "title is mandatory")
    private String title;

    @NotBlank(message = "description is mandatory")
    @NotEmpty(message = "description is mandatory")
    private String description;

    @NotNull(message = "duration is mandatory")
    private int durationHour;

    private String pathId;

    @Size(min=4,message="project must have at least 4 courses")
    private List<Long> courseIds;
    
    //if user finished the projects
    private Boolean finished;

}
