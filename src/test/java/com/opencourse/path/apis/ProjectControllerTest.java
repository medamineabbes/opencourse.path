package com.opencourse.path.apis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencourse.path.dtos.ProjectDto;
import com.opencourse.path.services.ProjectService;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
    
    @Autowired
    MockMvc mvc;
    @MockBean
    ProjectService service;

    private ObjectMapper mapper;
    private String baseUrl="/api/v1/path/project";

    @BeforeEach
    public void init(){
        mapper=new ObjectMapper();
    }

    @Test
    @DisplayName("should add project")
    public void addProjectTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("title", "title");
        body.put("description","description");
        body.put("durationHour",3);
        body.put("pathId","pathis");
        body.put("courseIds",List.of(1L,2L,3L));

        String payload=mapper.writeValueAsString(body);

        mvc.perform(
            post(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        
        verify(service).addProject(any(ProjectDto.class), any(Long.class));
    }

    @Test
    @DisplayName("")
    public void getProjectByIdTest() throws Exception{
        when(service.getProjectById("id", 15L)).thenReturn(new ProjectDto());
        mvc.perform(
            get(baseUrl + "/id")
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return list")
    public void getByPathIdTest() throws Exception{
        when(service.getByPathId("pathId", 15L)).thenReturn(List.of(new ProjectDto()));
        mvc.perform(
            get(baseUrl)
            .param("pathId", "pathId")
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should update project")
    public void updateProjectTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("title", "title");
        body.put("description","description");
        body.put("durationHour",3);
        body.put("pathId","pathis");
        body.put("courseIds",List.of(1L,2L,3L));

        String payload=mapper.writeValueAsString(body);

        mvc.perform(
            put(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        
        verify(service).updateProject(any(ProjectDto.class), any(Long.class));
    }

    @Test
    @DisplayName("should set project as finsihed")
    public void setProjectAsFinishedTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("userId",11L);
        body.put("projectId", "id");
        String payload=mapper.writeValueAsString(body);
        mvc.perform(
            put(baseUrl+"/finish")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(service).setProjectAsFinsihed(any(), any(Long.class));
    }
}
