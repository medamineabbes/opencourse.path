package com.opencourse.path.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PathDto {
    
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

    @NotNull
    private String topicId;


    //if user finished the path
    private Boolean finished;

}
