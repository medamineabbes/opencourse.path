package com.opencourse.path.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencourse.path.dtos.SubscribedPathDto;
import com.opencourse.path.dtos.SubscriptionRequestDto;
import com.opencourse.path.services.SubscriptionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/path/subscription")
@AllArgsConstructor
public class SubscriptionController {
    
    private SubscriptionService service;

    //only other services
    @PostMapping("/access")
    public ResponseEntity<Boolean> HasAccessToCourse(@RequestBody(required = true) Long userId,@RequestBody(required = true) Long courseId){
        Boolean hasAccess = service.userHasAccessToCourse(userId, courseId);
        return ResponseEntity.ok(hasAccess);
    }

    @PostMapping
    //only authentic users
    public ResponseEntity<Object> subscribe(@RequestBody(required = true) SubscriptionRequestDto subReqDto){
        Long userId=15L;
        service.subscribe(subReqDto, userId);
        return ResponseEntity.ok().build();
    }

    //all users
    @GetMapping
    public ResponseEntity<List<SubscribedPathDto>> getPathsBySubscription(){
        Long userId=15L;
        List<SubscribedPathDto> paths=service.getPathsByUserSub(userId);
        return ResponseEntity.ok(paths);
    }

}
