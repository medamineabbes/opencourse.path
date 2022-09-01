package com.opencourse.path.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserInfoDto {
    
    private String firstname;
    private String lastname;
    private LocalDateTime dateOfBirth;
}
