package com.opencourse.path.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.opencourse.path.dtos.ElementDto;
import com.opencourse.path.dtos.mappers.ElementMapper;
import com.opencourse.path.entities.OverviewElement;
import com.opencourse.path.entities.Path;
import com.opencourse.path.exceptions.ElementNotFoundException;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.exceptions.UnAuthorizedActionException;
import com.opencourse.path.repos.ElementRepo;
import com.opencourse.path.repos.PathRepo;

public class ElementServiceTest {
    
    private PathRepo pathRepo=mock(PathRepo.class);
    private ElementRepo elementRepo=mock(ElementRepo.class);
    private ElementMapper eMapper=new ElementMapper();
    private ElementService service;

    private Path front,back;
    private OverviewElement e1,e2,e3,e4;
    
    @BeforeEach
    public void init(){
        service=new ElementService(pathRepo, elementRepo, eMapper);

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

        front.setElements(List.of(e1,e2));
        back.setElements(List.of(e3,e4));
        e1.setPath(front);
        e2.setPath(front);
        e3.setPath(back);
        e4.setPath(back);
    }
    
    @Test
    @DisplayName("should throw path not found exception")
    public void addElementTest(){
        ElementDto dto=new ElementDto();
        dto.setPathId("id");
        assertThrows(PathNotFoundException.class, ()->{
            service.addElement(dto, 15L);
        });
    }

    @Test
    @DisplayName("should throw unAuthAction exception")
    public void addElementTest2(){
        ElementDto dto=new ElementDto();
        dto.setPathId("id");
        when(pathRepo.findById("id")).thenReturn(Optional.of(back));
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.addElement(dto, 15L);
        });
    }

    @Test
    @DisplayName("should add element")
    public void addElementTest3(){
        ElementDto dto=new ElementDto();
        dto.setPathId("id");
        when(pathRepo.findById("id")).thenReturn(Optional.of(back));
        service.addElement(dto, 2L);
        verify(elementRepo).save(any(OverviewElement.class));
    }

    @Test
    @DisplayName("should throw path not found exception")
    public void getElementByPathIdTest(){
        assertThrows(PathNotFoundException.class, ()->{
            service.getElementsByPathId("id",15L);
        });
    }

    @Test
    @DisplayName("should return elements")
    public void getElementsByPathIdTest2(){
        when(pathRepo.findById(back.getId())).thenReturn(Optional.of(back));
        List<ElementDto> elements=service.getElementsByPathId(back.getId(), 15L);
        assertEquals(2, elements.size());
    }
    @Test
    @DisplayName("should return elements")
    public void getElementsByPathIdTest3(){
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        List<ElementDto> elements=service.getElementsByPathId(front.getId(), 1L);
        assertEquals(2, elements.size());
    }

    @Test
    @DisplayName("should throw path not found exception")
    public void getElementsByPathIdTest4(){
        when(pathRepo.findById(front.getId())).thenReturn(Optional.of(front));
        assertThrows(PathNotFoundException.class, ()->{
            service.getElementsByPathId(back.getId(), 15L);
        });
        
    }

    @Test
    @DisplayName("should throw elemnt Not Found Exception")
    public void updateElementTest(){

        ElementDto dto=new ElementDto();
        dto.setContent("content");
        dto.setId("id");
        assertThrows(ElementNotFoundException.class, ()->{
            service.updateElement(dto, 15L);
        });
    }

    @Test
    @DisplayName("should throw UnAuthActionException")
    public void updateelementTest2(){
        ElementDto dto=new ElementDto();
        dto.setContent("content");
        dto.setId("id");
        when(elementRepo.findById("id")).thenReturn(Optional.of(e1));
        assertThrows(UnAuthorizedActionException.class, ()->{
            service.updateElement(dto, 2L);
        });
    }

    @Test
    @DisplayName("should update element")
    public void updateElementTest3(){
        ElementDto dto=new ElementDto();
        dto.setContent("content");
        dto.setId("id");
        when(elementRepo.findById("id")).thenReturn(Optional.of(e1));
        service.updateElement(dto, 1L);
        verify(elementRepo).save(any(OverviewElement.class));
    }

}
