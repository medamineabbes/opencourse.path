package com.opencourse.path.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencourse.path.dtos.ElementDto;
import com.opencourse.path.dtos.mappers.ElementMapper;
import com.opencourse.path.entities.OverviewElement;
import com.opencourse.path.entities.Path;
import com.opencourse.path.exceptions.ElementNotFoundException;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.exceptions.UnAuthorizedActionException;
import com.opencourse.path.repos.ElementRepo;
import com.opencourse.path.repos.PathRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ElementService {
    
    private final PathRepo pathRepo;
    private final ElementRepo elementRepo;
    private final ElementMapper eMapper;
    //CRUD ELEMENT  
    //only teacher
    public String addElement(ElementDto dto,Long userId){
        //make sure path exists
        Path path=pathRepo.findById(dto.getPathId())
        .orElseThrow(()->new PathNotFoundException(dto.getPathId()));

        //make sure user is creator
        if(!path.getCreatorId().equals(userId))
        throw new UnAuthorizedActionException();

        OverviewElement element=eMapper.toElement(dto);
        element.setId(null);
        element.setPath(path);

        elementRepo.save(element);
        
        return element.getId();
    }

    //all users
    public List<ElementDto> getElementsByPathId(String pathId,Long userId){
        Path path=pathRepo.findById(pathId)
        .orElseThrow(()->new PathNotFoundException(pathId));

        //if path is not active user must be creator
        if(!path.getActive() && !path.getCreatorId().equals(userId))
        throw new PathNotFoundException(pathId);

        return path.getElements()
        .stream()
        .map((element)->eMapper.toDto(element))
        .collect(Collectors.toList());
    }

    //only teacher
    public void updateElement(ElementDto dto,Long userId){
        //make sure element Exists
        OverviewElement element=elementRepo.findById(dto.getId())
        .orElseThrow(()->new ElementNotFoundException(dto.getId()));

        //make sure user is creator
        if(!element.getPath().getCreatorId().equals(userId))
        throw new UnAuthorizedActionException();

        element=eMapper.toElement(dto);
        elementRepo.save(element);
    }

    
}
