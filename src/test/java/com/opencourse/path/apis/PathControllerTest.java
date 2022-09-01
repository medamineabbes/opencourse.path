package com.opencourse.path.apis;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencourse.path.PathProperties;
import com.opencourse.path.dtos.PathDto;
import com.opencourse.path.services.PathService;

@WebMvcTest(PathController.class)
public class PathControllerTest {
    
    @MockBean
    PathProperties properies;
    @MockBean
    PathService service;
    @Autowired
    MockMvc mvc;

    private ObjectMapper mapper=new ObjectMapper();
    private String baseUrl="/api/v1/path";

    @Test
    @DisplayName("should add path")
    public void addPathTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("title", "tite");
        body.put("description", "desc");
        body.put("image", "image");
        body.put("durationMonth", 2);
        body.put("topicId", "id");
        String payload=mapper.writeValueAsString(body);

        mvc.perform(
            post(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return path")
    public void getPathByIdTest() throws Exception{
        when(service.getPathById("id",15L)).thenReturn(new PathDto());

        mvc.perform(
            get(baseUrl + "/id")
        ).andExpect(status().isOk());
    }
    

    @Test
    @DisplayName("should update path")
    public void updatePathTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("title", "tite");
        body.put("description", "desc");
        body.put("image", "image");
        body.put("durationMonth", 2);
        body.put("topicId", "id");
        String payload=mapper.writeValueAsString(body);
        mvc.perform(
            put(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("sould activate path")
    public void activatePathTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("pathId", "id");
        String payload=mapper.writeValueAsString(body);
        mvc.perform(
            put(baseUrl + "/activate")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should descativate Path")
    public void desactivatePathTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("pathId", "id");
        String payload=mapper.writeValueAsString(body);
        mvc.perform(
            put(baseUrl + "/desactivate")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return paths")
    public void searchByTitleTest() throws Exception{
        when(properies.getPageSize()).thenReturn(20);
        mvc.perform(
            get(baseUrl)
            .param("title", "t")
            .param("page", "1")
        ).andExpect(status().isOk());
    }

}
