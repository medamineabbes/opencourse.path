package com.opencourse.path.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ElementDto {
    
    private String id;

    @NotBlank(message = "title is mandatory")
    @NotEmpty(message = "title is mandatory")
    private String title;

    @Size(min = 100,message = "content min size is 100 characteres")
    private String content;

    private String pathId;
}
