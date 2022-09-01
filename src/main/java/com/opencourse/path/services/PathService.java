package com.opencourse.path.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.opencourse.path.PathProperties;
import com.opencourse.path.dtos.PathDto;
import com.opencourse.path.dtos.mappers.PathMapper;
import com.opencourse.path.entities.Certificate;
import com.opencourse.path.entities.Path;
import com.opencourse.path.entities.Topic;
import com.opencourse.path.exceptions.PathActivationException;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.exceptions.TopicNotFoundException;
import com.opencourse.path.exceptions.UnAuthorizedActionException;
import com.opencourse.path.repos.PathRepo;
import com.opencourse.path.repos.TopicRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PathService {
    private final TopicRepo topicRepo;
    private final PathRepo pathRepo;
    private final PathMapper pMApper;
    private final PathProperties properties;
    //CRUD PATH
    //only teacher
    public String addPath(PathDto dto,Long userId){
        
        //make sure topic exists
        Topic topic=topicRepo.findById(dto.getTopicId())
        .orElseThrow(()->new TopicNotFoundException(dto.getId()));

        Path path=pMApper.toPath(dto);
        path.setId(null);
        path.setCreatorId(userId);
        path.setActive(false);
        path.setTopic(topic);
        pathRepo.save(path);
        return path.getId();
    }

    //all users
    public PathDto getPathById(String id,Long userId){

        //make sure path exists
        Path path=pathRepo.findById(id)
        .orElseThrow(()->new PathNotFoundException(id));

        //make sure path is active or user is creator
        if(!path.getActive() && !userId.equals(path.getCreatorId()))
        throw new PathNotFoundException(id);
        
        PathDto dto=pMApper.toDto(path);

        Optional<Certificate> opCert=path
        .getCertificates()
        .stream()
        .filter(cert->cert.getUserId().equals(userId))
        .findAny();
        
        if(opCert.isPresent())
        dto.setFinished(true);
        else
        dto.setFinished(false);

        return dto;
    }

    //only teachers
    public void updatePath(PathDto dto,Long userId){

        //make sure path exists
        Path path=pathRepo.findById(dto.getId())
        .orElseThrow(()->new PathNotFoundException(dto.getId()));

        //make sure user is creator
        if(!path.getCreatorId().equals(userId))
        throw new UnAuthorizedActionException();

        //update path
        path=pMApper.toPath(dto);
        pathRepo.save(path);
    }

    //OTHER OPERATIONS
    //only teacher
    public void activatePath(String pathId,Long userId){
        
        Path path=pathRepo.findById(pathId)
        .orElseThrow(()->new PathNotFoundException(pathId));

        //user must be creator 
        if(!path.getCreatorId().equals(userId))
        throw new UnAuthorizedActionException();

        //at least 4 projects 
        if(path.getProjects().size() < properties.getMinNumProject())
        throw new PathActivationException("path must have at least 4 projects");

        //at least 3 overview elements
        if(path.getElements().size() < properties.getMinNumElement())
        throw new PathActivationException("path must have at least 3 overview elements");

        //activation
        path.setActive(true);
        pathRepo.save(path);
    }

    //only teacher
    public void deactivatePath(String pathId,Long userId){
        
        Path path=pathRepo.findById(pathId)
        .orElseThrow(()->new PathNotFoundException(pathId));

        //user is creator
        if(!path.getCreatorId().equals(userId))
        throw new UnAuthorizedActionException();

        path.setActive(false);
        pathRepo.save(path);
    }

    //all users
    public Page<PathDto> searchByTitle(String title,Long userId,Pageable pageable){
        
        //get paths 
        Page<Path> paths=pathRepo
        .searchByTitleContainingAndActive(title, true, pageable);

        //map paths to path dto
        Page<PathDto> pathsDto=paths
        .map((path)->pMApper.toDto(path));

        List<Path> listPaths=paths.getContent();
        List<PathDto> listPathdtos=pathsDto.getContent();


        for(int i=0;i<listPaths.size();i++){
            Path path=listPaths.get(i);
            //if userfinished path
            if(
                path.getCertificates() != null
                &&
                path.getCertificates()
                .stream()
                .filter((cert)->cert.getUserId().equals(userId))
                .findAny()
                .isPresent()
            )
            listPathdtos.get(i).setFinished(true);
            else
            listPathdtos.get(i).setFinished(false);
        }
        return pathsDto;

    }

}
