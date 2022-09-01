package com.opencourse.path.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencourse.path.dtos.FinishProjectDto;
import com.opencourse.path.dtos.ProjectDto;
import com.opencourse.path.dtos.mappers.ProjectMapper;
import com.opencourse.path.entities.FinishedProject;
import com.opencourse.path.entities.Path;
import com.opencourse.path.entities.Project;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.exceptions.ProjectNotFoundException;
import com.opencourse.path.exceptions.UnAuthorizedActionException;
import com.opencourse.path.exceptions.UserNotSubscribedException;
import com.opencourse.path.externalServices.UserService;
import com.opencourse.path.repos.PathRepo;
import com.opencourse.path.repos.ProjectRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final PathRepo pathRepo;
    private final ProjectMapper pMapper;
    private final UserService userService;

    //CRUD PROJECT
    //only teacher
    public String addProject(ProjectDto dto,Long userId){
        //make sure path exists
        Path path=pathRepo.findById(dto.getPathId())
        .orElseThrow(()->new PathNotFoundException(dto.getPathId()));

        //make sure user is creator
        if(!path.getCreatorId().equals(userId))
        throw new UnAuthorizedActionException();

        Project project=pMapper.toProject(dto);
        project.setPath(path);
        project.setId(null);
        projectRepo.save(project);
        return project.getId();
    }

    //all users 
    public ProjectDto getProjectById(String id,Long userId){

        //make sure project exists
        Project project=projectRepo.findById(id)
        .orElseThrow(()->new ProjectNotFoundException(id));

        //if not active user must be creator
        if(!project.getPath().getActive() && !project.getPath().getCreatorId().equals(userId)) 
        throw new ProjectNotFoundException(id);

        ProjectDto dto=pMapper.toDto(project);

        //if user finished the project 
        if(
            project.getFinished()!=null
            &&
            project.getFinished()
            .stream()
            .filter((finished)->finished.getUserId().equals(userId))
            .findAny()
            .isPresent()            
        )
        dto.setFinished(true);
        else
        dto.setFinished(false);
    
        return dto;
    }
    
    //all users 
    public List<ProjectDto> getByPathId(String pathId,Long userId){

        //make sure path exists
        Path path=pathRepo.findById(pathId)
        .orElseThrow(()->new PathNotFoundException(pathId));

        //if not active and user not creator
        if(!path.getActive() && !path.getCreatorId().equals(userId))
        throw new PathNotFoundException(pathId);

        //convert to dto
        List<ProjectDto> listDtos=path.getProjects()
        .stream()
        .map((project)->pMapper.toDto(project))
        .collect(Collectors.toList());

        for(int i=0;i<listDtos.size();i++){
            Project project=path.getProjects().get(i);
            //if user finished the project
            if(
                project.getFinished()!=null
                &&
                project.getFinished()
                .stream()
                .filter(finished->finished.getUserId().equals(userId))
                .findAny()
                .isPresent()
            )
            listDtos.get(i).setFinished(true);
            else
            listDtos.get(i).setFinished(false);
        }

        return listDtos;
    }

    //only teachers
    public void updateProject(ProjectDto dto,Long userId){
        //make sure project exists
        Project project=projectRepo.findById(dto.getId())
        .orElseThrow(()->new ProjectNotFoundException(dto.getId()));

        //make sure user is creator
        if(!project.getPath().getCreatorId().equals(userId))
        throw new UnAuthorizedActionException();

        project=pMapper.toProject(dto);
        projectRepo.save(project);
    }

    //only mentor
    public void setProjectAsFinsihed(FinishProjectDto dto,Long userId){
        //make sure project exists
        Project project=projectRepo.findById(dto.getProjectId())
        .orElseThrow(()->new ProjectNotFoundException(dto.getProjectId()));
        
        //make sure mentor is for user
        if(!userService.isUserMentor(dto.getUserId(), userId)){
            throw new UnAuthorizedActionException();
        }

        //make sure user is subscribed to path
        if(
            project.getPath()
            .getSubscription()
            .stream()
            .filter((sub)->sub.getUserId().equals(dto.getUserId())
                &&
                sub.getDate().isBefore(LocalDateTime.now())
                &&
                sub.getDate().plusMonths(sub.getType().getDurationMonth()).isAfter(LocalDateTime.now()))
            .findAny()
            .isEmpty()
        )
        throw new UserNotSubscribedException(dto.getUserId(), project.getPath().getId());

        FinishedProject finishedProject=new FinishedProject();
        finishedProject.setDate(LocalDateTime.now());
        finishedProject.setUserId(dto.getUserId());

        project.getFinished().add(finishedProject);
        
        projectRepo.save(project);
    }

    
}
