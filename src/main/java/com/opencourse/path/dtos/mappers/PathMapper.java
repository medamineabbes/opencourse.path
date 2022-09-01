package com.opencourse.path.dtos.mappers;

import org.springframework.stereotype.Component;

import com.opencourse.path.dtos.PathDto;
import com.opencourse.path.entities.Path;

@Component
public class PathMapper {
    

    public Path toPath(PathDto dto){
        Path path=new Path();
        path.setDescription(dto.getDescription());
        path.setDurationMonth(dto.getDurationMonth());
        path.setId(dto.getId());
        path.setImage(dto.getImage());
        path.setTitle(dto.getTitle());
        return path;
    }

    public PathDto toDto(Path path){
        PathDto dto=new PathDto();
        dto.setDescription(path.getDescription());
        dto.setDurationMonth(path.getDurationMonth());
        dto.setId(path.getId());
        dto.setImage(path.getImage());
        dto.setTitle(path.getTitle());
        dto.setTopicId(path.getTopic().getId());
        dto.setCreatorId(path.getCreatorId());
        dto.setActive(path.getActive());
        return dto;
    }
}
