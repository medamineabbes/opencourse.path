package com.opencourse.path.dtos.mappers;

import org.springframework.stereotype.Component;

import com.opencourse.path.dtos.ProjectDto;
import com.opencourse.path.entities.Project;

@Component
public class ProjectMapper {
    
    public Project toProject(ProjectDto dto){
        Project project=new Project();
        project.setCourseIds(dto.getCourseIds());
        project.setDescription(dto.getDescription());
        project.setDurationHour(dto.getDurationHour());
        project.setId(dto.getId());
        project.setTitle(dto.getTitle());
        return project;
    }

    public ProjectDto toDto(Project project){
        ProjectDto dto=new ProjectDto();
        dto.setCourseIds(project.getCourseIds());
        dto.setDescription(project.getDescription());
        dto.setDurationHour(project.getDurationHour());
        dto.setId(project.getId());
        dto.setPathId(project.getPath().getId());
        dto.setTitle(project.getTitle());
        return dto;
    }
}
