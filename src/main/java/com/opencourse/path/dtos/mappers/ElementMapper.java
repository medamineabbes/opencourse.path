package com.opencourse.path.dtos.mappers;

import org.springframework.stereotype.Component;

import com.opencourse.path.dtos.ElementDto;
import com.opencourse.path.entities.OverviewElement;

@Component
public class ElementMapper {
    
    public OverviewElement toElement(ElementDto dto){
        OverviewElement element=new OverviewElement();
        element.setContent(dto.getContent());
        element.setTitle(dto.getTitle());
        element.setId(dto.getId());
        return element;
    }

    public ElementDto toDto(OverviewElement element){
        ElementDto dto=new ElementDto();
        dto.setContent(element.getContent());
        dto.setId(element.getId());
        dto.setPathId(element.getPath().getId());
        dto.setTitle(element.getTitle());
        return dto;
    }
}
