package com.opencourse.path.apis;

import java.util.List;

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

import com.opencourse.path.dtos.FinishProjectDto;
import com.opencourse.path.dtos.ProjectDto;
import com.opencourse.path.services.ProjectService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/project")
@AllArgsConstructor
public class ProjectController {

    private ProjectService service;

    //only teachers
    @PostMapping
    public ResponseEntity<String> addProject(@RequestBody(required = true) ProjectDto dto){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        String id=service.addProject(dto, userId);
        return ResponseEntity.ok(id);
    }

    //all users
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable(required = true) String id){
        Long userId;
        try{
            userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch(Exception e){
            userId=null;
        }
        ProjectDto dto=service.getProjectById(id, userId);
        return ResponseEntity.ok(dto);
    }

    //all users
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getByPathId(@RequestParam(required = true) String pathId){
        Long userId;
        try{
            userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        }catch(Exception e){
            userId=null;
        }
        List<ProjectDto> projects=service.getByPathId(pathId, userId);
        return ResponseEntity.ok(projects);
    }

    //only teachers
    @PutMapping
    public ResponseEntity<Object> updateProject(@RequestBody(required = true) ProjectDto dto){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        service.updateProject(dto, userId);;
        return ResponseEntity.ok().build();
    }

    //only mentor
    @PutMapping("/finish")
    public ResponseEntity<Object> setProjectAsFinished(@RequestBody(required = true) FinishProjectDto dto){
        Long userId=Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        service.setProjectAsFinsihed(dto, userId);
        return ResponseEntity.ok().build();
    }

}
