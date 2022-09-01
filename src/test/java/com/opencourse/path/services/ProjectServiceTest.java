package com.opencourse.path.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.opencourse.path.dtos.FinishProjectDto;
import com.opencourse.path.dtos.ProjectDto;
import com.opencourse.path.dtos.mappers.ProjectMapper;
import com.opencourse.path.entities.Certificate;
import com.opencourse.path.entities.FinishedProject;
import com.opencourse.path.entities.OverviewElement;
import com.opencourse.path.entities.Path;
import com.opencourse.path.entities.Topic;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.exceptions.ProjectNotFoundException;
import com.opencourse.path.exceptions.UnAuthorizedActionException;
import com.opencourse.path.exceptions.UserNotSubscribedException;
import com.opencourse.path.entities.Project;
import com.opencourse.path.entities.Subscription;
import com.opencourse.path.entities.SubscriptionType;
import com.opencourse.path.externalServices.UserService;
import com.opencourse.path.repos.PathRepo;
import com.opencourse.path.repos.ProjectRepo;

public class ProjectServiceTest {
    private ProjectRepo projectRepo=mock(ProjectRepo.class);
    private PathRepo pathRepo=mock(PathRepo.class);
    private ProjectMapper pMapper=new ProjectMapper();
    private UserService userService=mock(UserService.class);
    private ProjectService service;

    private Topic web;
    private Path front,back;
    private Project p1,p2,p3,p4,p5;
    private OverviewElement e1,e2,e3,e4;
    private Certificate c1,c2,c3;
    private FinishedProject f1,f2,f3,f4,f5,f6,f7,f8,f9;


    @BeforeEach
    public void init(){
        service=new ProjectService(projectRepo, pathRepo, pMapper, userService);

        web=new Topic();
        web.setName("web");
        web.setId("id t1");

        front=new Path();back=new Path();
        front.setActive(false);
        front.setCreatorId(1L);
        front.setDescription("front end dev");
        front.setDurationMonth(2);
        front.setId("id front");
        front.setImage("image1");
        front.setTitle("frontend");

        back.setActive(true);
        back.setCreatorId(2L);
        back.setDescription("back end dev");
        back.setDurationMonth(2);
        back.setId("id back");
        back.setImage("image2");
        back.setTitle("backend");
        p1=new Project();p2=new Project();p3=new Project();p4=new Project();p5=new Project();
        
        p1.setCourseIds(List.of(1L,2L,3L));
        p1.setDescription("project 1 ");
        p1.setDurationHour(15);
        p1.setId("id p 1");
        p1.setTitle("project1");

        p2.setCourseIds(List.of(4L,5L,6L));
        p2.setDescription("project 2 ");
        p2.setDurationHour(10);
        p2.setId("id p 2");
        p2.setTitle("project2");

        p3.setCourseIds(List.of(7L,8L,9L));
        p3.setDescription("project 3 ");
        p3.setDurationHour(12);
        p3.setId("id p 3");
        p3.setTitle("project3");

        p4.setCourseIds(List.of(10L,11L,12L));
        p4.setDescription("project 4 ");
        p4.setDurationHour(8);
        p4.setId("id p 4");
        p4.setTitle("project4");

        p5.setCourseIds(List.of(13L,22L,43L));
        p5.setDescription("project 5 ");
        p5.setDurationHour(4);
        p5.setId("id p 5");
        p5.setTitle("project5");

        e1=new OverviewElement();e2=new OverviewElement();e3=new OverviewElement();e4=new OverviewElement();

        e1.setContent("content e1");
        e1.setId("id e1");
        e1.setTitle("title e1");

        e2.setContent("content e2");
        e2.setId("id e2");
        e2.setTitle("title e2");

        e3.setContent("content e3");
        e3.setId("id e3");
        e3.setTitle("title e3");

        e4.setContent("content e4");
        e4.setId("id e4");
        e4.setTitle("title e4");
        
        c1=new Certificate();c2=new Certificate();c3=new Certificate();

        c1.setDate(LocalDateTime.now().minusDays(2));
        c1.setId("id c1");
        c1.setUserId(80L);

        c2.setDate(LocalDateTime.now().minusDays(7));
        c2.setId("id c2");
        c2.setUserId(19L);
        
        c3.setDate(LocalDateTime.now().minusDays(15));
        c3.setId("id c3");
        c3.setUserId(20L);
        
        f1=new FinishedProject();f4=new FinishedProject();f7=new FinishedProject();
        f2=new FinishedProject();f5=new FinishedProject();f8=new FinishedProject();
        f3=new FinishedProject();f6=new FinishedProject();f9=new FinishedProject();

        f1.setDate(LocalDateTime.now().minusMonths(1));
        f1.setUserId(80L);
        f2.setDate(LocalDateTime.now().minusMonths(1));
        f2.setUserId(80L);
        f3.setDate(LocalDateTime.now().minusMonths(1));
        f3.setUserId(80L);

        f4.setDate(LocalDateTime.now().minusMonths(1));
        f4.setUserId(19L);
        f5.setDate(LocalDateTime.now().minusMonths(1));
        f5.setUserId(19L);
        f6.setDate(LocalDateTime.now().minusMonths(1));
        f6.setUserId(19L);

        f7.setDate(LocalDateTime.now().minusMonths(1));
        f7.setUserId(20L);
        f8.setDate(LocalDateTime.now().minusMonths(1));
        f8.setUserId(20L);
        f9.setDate(LocalDateTime.now().minusMonths(1));
        f9.setUserId(20L);
        
        web.setPaths(List.of(front,back));
        front.setTopic(web);
        back.setTopic(web);

        front.setProjects(List.of(p1,p2));
        back.setProjects(List.of(p3,p4,p5));
        p1.setPath(front);
        p2.setPath(front);
        p3.setPath(back);
        p4.setPath(back);
        p5.setPath(back);

        front.setElements(List.of(e1,e2));
        back.setElements(List.of(e3,e4));
        e1.setPath(front);
        e2.setPath(front);
        e3.setPath(back);
        e4.setPath(back);

        p3.setFinished(List.of(f1,f4,f7));
        p4.setFinished(List.of(f2,f5,f8));
        p5.setFinished(List.of(f3,f6,f9));

        back.setCertificates(List.of(c1,c2,c3));
        c1.setPath(back);
        c2.setPath(back);
        c3.setPath(back);

    }

    @Test
    @DisplayName("should throw path not found")
    public void addProjectTest(){
        ProjectDto dto=new ProjectDto();
        dto.setPathId("pathId");
        assertThrows(PathNotFoundException.class, ()->{
            service.addProject(dto, 15L);
        });
    }

    @Test
    @DisplayName("should throw unauthaction")
    public void addProjectTest2(){
        ProjectDto dto=new ProjectDto();
        dto.setPathId(front.getId());
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.addProject(dto, 15L);
        });
    }

    @Test
    @DisplayName("should add project")
    public void addProjectTest3(){
        ProjectDto dto=new ProjectDto();
        dto.setPathId(front.getId());
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        service.addProject(dto, 1L);
        verify(projectRepo).save(any(Project.class));
    }

    @Test
    @DisplayName("should throw project not found")
    public void getProjectByIdTest(){
        assertThrows(ProjectNotFoundException.class,()->{
            service.getProjectById("id", 15L);
        });
    }

    @Test
    @DisplayName("should throw project not found (not active)")
    public void getProjectByIdTest2(){
        when(projectRepo.findById(p1.getId())).thenReturn(Optional.of(p1));
        assertThrows(ProjectNotFoundException.class, ()->{
            service.getProjectById(p1.getId(), 22L);
        });
    }

    @Test
    @DisplayName("should return project")
    public void getProjectByIdTest3(){
        when(projectRepo.findById(p1.getId())).thenReturn(Optional.of(p1));
        ProjectDto dto=service.getProjectById(p1.getId(), 1L);
        assertEquals(p1.getTitle(), dto.getTitle());
    }

    @Test
    @DisplayName("should throw path not found")
    public void getByPathIdTest(){
        assertThrows(PathNotFoundException.class, ()->{
            service.getByPathId("pathId", 15L);
        });
    }

    @Test
    @DisplayName("should throw path not found exception (not active)")
    public void getByPathIdTest2(){
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        assertThrows(PathNotFoundException.class, ()->{
            service.getByPathId(front.getId(), 2L);
        });
    }

    @Test
    @DisplayName("should return projects (not active)")
    public void getByPathIdTest3(){
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));

        List<ProjectDto> projects=service.getByPathId(front.getId(), front.getCreatorId());
        assertEquals(front.getProjects().size(), projects.size());
        assertEquals(front.getProjects().get(0).getTitle(), projects.get(0).getTitle());
    }

    @Test
    @DisplayName("should return projects (active)(finished)")
    public void getByPathIdTest4(){
        when(pathRepo.findById(back.getId())).thenReturn(Optional.of(back));
        List<ProjectDto> projects=service.getByPathId(back.getId(), 80L);
        assertEquals(back.getProjects().size(), projects.size());
        assertEquals(back.getProjects().get(0).getTitle(), projects.get(0).getTitle());
        for(int i=0;i<projects.size();i++){
            assertTrue(projects.get(i).getFinished());
        }
    }

    @Test
    @DisplayName("should return projects (active)(not finished)")
    public void getByPathIdTest5(){
        when(pathRepo.findById(back.getId())).thenReturn(Optional.of(back));
        List<ProjectDto> projects=service.getByPathId(back.getId(), 70L);
        assertEquals(back.getProjects().size(), projects.size());
        assertEquals(back.getProjects().get(0).getTitle(), projects.get(0).getTitle());
        for(int i=0;i<projects.size();i++){
            assertFalse(projects.get(i).getFinished());
        }
    }


    @Test
    @DisplayName("shoul throw project not found exception")
    public void updateProjectTest(){
        ProjectDto dto=new ProjectDto();
        dto.setId("id");

        assertThrows(ProjectNotFoundException.class, ()->{
            service.updateProject(dto, 15L);
        });
    }

    @Test
    @DisplayName("should throw un auth action exception")
    public void updateProjectTest2(){
        ProjectDto dto=new ProjectDto();
        dto.setId(p1.getId());
        when(projectRepo.findById(p1.getId())).thenReturn(Optional.of(p1));
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.updateProject(dto,15L);
        });
    }

    @Test
    @DisplayName("should update project")
    public void updateProjectTest3(){
        ProjectDto dto=new ProjectDto();
        dto.setId(p1.getId());
        when(projectRepo.findById(p1.getId())).thenReturn(Optional.of(p1));
        service.updateProject(dto,p1.getPath().getCreatorId());
        verify(projectRepo).save(any(Project.class));
    }

    @Test
    @DisplayName("should throw project Not foundException")
    public void setProjectAsFinishedTest(){
        FinishProjectDto dto=new FinishProjectDto();
        dto.setProjectId("pid");
        dto.setUserId(1L);
        assertThrows(ProjectNotFoundException.class, ()->{
            service.setProjectAsFinsihed(dto, 15L);
        });
    }

    @Test
    @DisplayName("should throw un auth action exception")
    public void setProjectAsFinishedTest2(){
        FinishProjectDto dto=new FinishProjectDto();
        dto.setProjectId("pid");
        dto.setUserId(1L);
        when(projectRepo.findById("pid")).thenReturn(Optional.of(p3));
        when(userService.isUserMentor(1L, 2L)).thenReturn(false);
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.setProjectAsFinsihed(dto, 2L);
        });
    }

    @Test
    @DisplayName("should throw user not sun exception")
    public void setProjectAsFinishedTest3(){
        p3.getPath().setSubscription(List.of());
        FinishProjectDto dto=new FinishProjectDto();
        dto.setProjectId("pid");
        dto.setUserId(1L);
        when(projectRepo.findById("pid")).thenReturn(Optional.of(p3));
        when(userService.isUserMentor(1L, 2L)).thenReturn(true);

        assertThrows(UserNotSubscribedException.class, ()->{
            service.setProjectAsFinsihed(dto, 2L);
        });
    }

    @Test
    @DisplayName("should set project as finished")
    public void setProjectAsFinishedTest4(){
        SubscriptionType type=new SubscriptionType();
        type.setDescription("description");
        type.setDurationMonth(2);
        Subscription sub=new Subscription();
        sub.setDate(LocalDateTime.now().minusMonths(1));
        sub.setUserId(15L);
        sub.setType(type);
        type.setSubscription(List.of(sub));
        p3.getPath().setSubscription(List.of(sub));
        p3.setFinished(new ArrayList<FinishedProject>());
        FinishProjectDto dto=new FinishProjectDto();
        dto.setProjectId("pid");
        dto.setUserId(15L);
        when(projectRepo.findById("pid")).thenReturn(Optional.of(p3));
        when(userService.isUserMentor(15L, 2L)).thenReturn(true);

        service.setProjectAsFinsihed(dto, 2L);
        verify(projectRepo).save(any(Project.class));
    }


}
