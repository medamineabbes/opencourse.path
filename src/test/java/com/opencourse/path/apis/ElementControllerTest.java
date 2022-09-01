package com.opencourse.path.apis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencourse.path.dtos.ElementDto;
import com.opencourse.path.services.ElementService;


@WebMvcTest(ElementController.class)
public class ElementControllerTest {
    @MockBean
    ElementService service;
    @Autowired
    MockMvc mvc;

    private ObjectMapper mapper=new ObjectMapper();
    private String baseUrl="/api/v1/path/element";

    @Test
    @DisplayName("should add Element")
    public void addElementTest() throws Exception{
        Map<String,Object> body=new HashMap<>();
        body.put("title", "title");
        body.put("content","content");
        body.put("pathId", "pathId");
        String payload=mapper.writeValueAsString(body);

        mvc.perform(
            post(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(service).addElement(any(ElementDto.class), any(Long.class));
    }

    @Test
    @DisplayName("should return list")
    public void getElementByPathIdTest() throws Exception{
        when(service.getElementsByPathId("id", 15L)).thenReturn(List.of(new ElementDto()));
        mvc.perform(
            get(baseUrl)
            .param("pathId","id")
        ).andExpect(status().isOk());

    }

    @Test
    @DisplayName("should update element")
    public void updateElementTest() throws Exception{
        
        Map<String,Object> body=new HashMap<>();
        body.put("title", "title");
        body.put("content","content");
        body.put("pathId", "pathId");
        String payload=mapper.writeValueAsString(body);

        mvc.perform(
            put(baseUrl)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(service).updateElement(any(ElementDto.class), any(Long.class));
    }


}
