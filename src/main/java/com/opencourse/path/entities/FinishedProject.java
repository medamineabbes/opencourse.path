package com.opencourse.path.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FinishedProject {
    
    private Long userId;
    private LocalDateTime date;
    
}
