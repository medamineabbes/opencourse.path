package com.opencourse.path.apis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencourse.path.PathProperties;
import com.opencourse.path.dtos.PathDto;
import com.opencourse.path.services.PathService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/path")
@AllArgsConstructor
public class PathController {
    
    private PathService service;
    private PathProperties properties;

    //only teachers
    @PostMapping
    public ResponseEntity<String> addPath(@RequestBody(required = true) PathDto dto){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        String id=service.addPath(dto, userId);
        return ResponseEntity.ok(id);
    }

    //all users
    @GetMapping("/{pathId}")
    public ResponseEntity<PathDto> getPathById(@PathVariable(required = true) String pathId){
        Long userId;
        try{
            userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch(Exception e){
            userId=null;
        }
        PathDto path=service.getPathById(pathId, userId);
        return ResponseEntity.ok(path);
    }

    //only teacher
    @PutMapping
    public ResponseEntity<Object> updatePath(@RequestBody(required = true) PathDto dto){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        service.updatePath(dto, userId);
        return ResponseEntity.ok().build();
    }

    //only teacher
    @PutMapping("/activate")
    public ResponseEntity<Object> activatePath(@RequestBody(required = true) String pathId){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        service.activatePath(pathId, userId);
        return ResponseEntity.ok().build();
    }

    //only teacher
    @PutMapping("/desactivate")
    public ResponseEntity<Object> desactivatePath(@RequestBody(required = true) String pathId){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        service.deactivatePath(pathId, userId);
        return ResponseEntity.ok().build();
    }
    
    //all users
    @GetMapping
    public ResponseEntity<Page<PathDto>> seachByTitle(@RequestParam(required = true) String title,@RequestParam(defaultValue = "0") int page){
        Long userId;
        try{
            userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch(Exception e){
            userId=null;
        }
        Pageable pageable =PageRequest.of(page, properties.getPageSize());
        Page<PathDto> result=service.searchByTitle(title, userId, pageable);
        return ResponseEntity.ok(result);
    }

}
