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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.opencourse.path.PathProperties;
import com.opencourse.path.dtos.PathDto;
import com.opencourse.path.dtos.mappers.PathMapper;
import com.opencourse.path.entities.Certificate;
import com.opencourse.path.entities.FinishedProject;
import com.opencourse.path.entities.OverviewElement;
import com.opencourse.path.entities.Path;
import com.opencourse.path.entities.Project;
import com.opencourse.path.entities.Topic;
import com.opencourse.path.exceptions.PathActivationException;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.exceptions.TopicNotFoundException;
import com.opencourse.path.exceptions.UnAuthorizedActionException;
import com.opencourse.path.repos.PathRepo;
import com.opencourse.path.repos.TopicRepo;

public class PathServiceTest {

    private  TopicRepo topicRepo=mock(TopicRepo.class);
    private  PathRepo pathRepo=mock(PathRepo.class);
    private PathProperties properties=mock(PathProperties.class);
    private  PathMapper pMApper=new PathMapper();
    private PathService service;


    private Topic web;
    private Path front,back;
    private Project p1,p2,p3,p4,p5;
    private OverviewElement e1,e2,e3,e4;
    private Certificate c1,c2,c3;
    private FinishedProject f1,f2,f3,f4,f5,f6,f7,f8,f9;
    
    @BeforeEach
    public void init(){
        this.service=new PathService(topicRepo, pathRepo, pMApper,properties);

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
    @DisplayName("should add PAth")
    public void addPathTest(){
        PathDto dto=new PathDto();
        dto.setDescription("description");
        dto.setDurationMonth(2);
        dto.setImage("image");
        dto.setTitle("title");
        dto.setTopicId("id t1");

        when(topicRepo.findById("id t1")).thenReturn(Optional.of(web));
        service.addPath(dto, 15L);
        verify(pathRepo).save(any(Path.class));
    }

    @Test
    @DisplayName("should throw topic not found exception")
    public void addPathTest2(){

        PathDto dto=new PathDto();
        dto.setDescription("description");
        dto.setDurationMonth(2);
        dto.setImage("image");
        dto.setTitle("title");
        dto.setTopicId("id t1");

        assertThrows(TopicNotFoundException.class, ()->{
            service.addPath(dto, 15L);
        });
    }

    @Test
    @DisplayName("should throw path not found ewception")
    public void getPathByIdTest(){
        assertThrows(PathNotFoundException.class, ()->{
            service.getPathById("id", 15L);
        });
    }

    @Test
    @DisplayName("should return path with finished true")
    public void getPathByIdTest2(){
        when(pathRepo.findById(back.getId())).thenReturn(Optional.of(back));
        PathDto dto=service.getPathById(back.getId(),80L );

        assertEquals(true, dto.getFinished());
    }

    @Test
    @DisplayName("should return path with finished false")
    public void getPathByIdTest3(){
        when(pathRepo.findById(back.getId())).thenReturn(Optional.of(back));
        PathDto dto=service.getPathById(back.getId(),81L );

        assertEquals(false, dto.getFinished());
    }
    
    /*
     * test not active paths
     */

    @Test
    @DisplayName("should throw path not found exception")
    public void updatePathTest(){
        PathDto dto=new PathDto();
        dto.setId(back.getId());
        assertThrows(PathNotFoundException.class, ()->{
            service.updatePath(dto,15L );
        });
    }

    @Test
    @DisplayName("should throw unauthaction exception")
    public void updatePathTest2(){
        PathDto dto=new PathDto();
        dto.setId(back.getId());
        when(pathRepo.findById(dto.getId())).thenReturn(Optional.of(back));
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.updatePath(dto,1L );
        });
    }

    @Test
    @DisplayName("should update path")
    public void updatePathTest3(){
        PathDto dto=new PathDto();
        dto.setId(back.getId());
        when(pathRepo.findById(dto.getId())).thenReturn(Optional.of(back));

        service.updatePath(dto, 2L);
        verify(pathRepo).save(any(Path.class));

    }


    @Test
    @DisplayName("should throw path activation exception")
    public void activatePathTest(){
        when(properties.getMinNumProject()).thenReturn(4);
        when(properties.getMinNumElement()).thenReturn(3);
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        Exception e=assertThrows(PathActivationException.class, ()->{
            service.activatePath(front.getId(), 1L);
        });

        assertEquals("path must have at least 4 projects", e.getMessage());
    }

    @Test
    @DisplayName("should throw path activation exception")
    public void activatePathTest2(){
        Project pro1=new Project();
        Project pro2=new Project();
        List<Project> list=front.getProjects();
        front.setProjects(List.of(list.get(0),list.get(1),pro1,pro2));
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        when(properties.getMinNumProject()).thenReturn(4);
        when(properties.getMinNumElement()).thenReturn(3);
        Exception e=assertThrows(PathActivationException.class, ()->{
            service.activatePath(front.getId(), 1L);
        });
        assertEquals("path must have at least 3 overview elements", e.getMessage());
    }

    @Test
    @DisplayName("should throw unAuthActionException")
    public void activatePathTest3(){
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        when(properties.getMinNumProject()).thenReturn(4);
        when(properties.getMinNumElement()).thenReturn(3);
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.activatePath(front.getId(), 2L);
        });
    }

    @Test
    @DisplayName("should activate path")
    public void activatePathTest4(){
        Project pro1=new Project();
        Project pro2=new Project();
        OverviewElement element=new OverviewElement();
        List<OverviewElement> elements=front.getElements();
        List<Project> list=front.getProjects();
        front.setProjects(List.of(list.get(0),list.get(1),pro1,pro2));
        front.setElements(List.of(elements.get(0),elements.get(1),element));
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        when(properties.getMinNumProject()).thenReturn(4);
        when(properties.getMinNumElement()).thenReturn(3);
        service.activatePath(front.getId(), 1L);
        verify(pathRepo).save(any(Path.class));
    }

    @Test
    @DisplayName("should throw un auth action exception")
    public void deactivatePathTest(){
        when(pathRepo.findById(back.getId())).thenReturn(Optional.of(back));
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.deactivatePath(back.getId(), 1L);
        });
    }

    @Test
    @DisplayName("should deactivate path")
    public void deactivatePathTest2(){
        when(pathRepo.findById(back.getId())).thenReturn(Optional.of(back));
        service.deactivatePath(back.getId(), 2L);
        verify(pathRepo).save(any(Path.class));
    }


    @Test
    @DisplayName("should return back finished")
    public void searchByTitleTest(){
        List<Path> paths=List.of(front,back);
        PageImpl<Path> page=new PageImpl<>(paths);

        when(pathRepo.searchByTitleContainingAndActive("", true, PageRequest.of(0, 10)))
        .thenReturn(page);

        Page<PathDto> p=service.searchByTitle("", 80L, PageRequest.of(0, 10));
        assertEquals(front.getId(), p.getContent().get(0).getId());
        assertEquals(back.getId(), p.getContent().get(1).getId());
        assertTrue(p.getContent().get(1).getFinished());
        assertFalse(p.getContent().get(0).getFinished());
    }

}
