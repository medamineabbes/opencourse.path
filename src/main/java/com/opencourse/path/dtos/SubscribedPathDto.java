package com.opencourse.path.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribedPathDto {
    private PathDto pathDto;
    private LocalDateTime endsAt;
    private Boolean activeSubscription;
}
