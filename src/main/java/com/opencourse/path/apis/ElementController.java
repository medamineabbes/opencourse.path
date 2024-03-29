package com.opencourse.path.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencourse.path.dtos.ElementDto;
import com.opencourse.path.services.ElementService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/element")
@AllArgsConstructor
public class ElementController {
    
    private ElementService service;

    //only teachers
    @PostMapping
    public ResponseEntity<String> addElement(@RequestBody(required = true) ElementDto dto){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        String id=service.addElement(dto, userId);
        return ResponseEntity.ok(id);
    }

    //all users
    @GetMapping
    public ResponseEntity<List<ElementDto>> getElementByPathId(@RequestParam(required = true) String pathId){
        Long userId;
        try{
            userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch(Exception e){
            userId=null;
        }
        List<ElementDto> elements=service.getElementsByPathId(pathId, userId);
        return ResponseEntity.ok(elements);
    }

    //only teacher
    @PutMapping
    public ResponseEntity<Object> updateElement(@RequestBody(required = true) ElementDto dto){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        service.updateElement(dto, userId);
        return ResponseEntity.ok().build();
    }

}
