package com.opencourse.path.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencourse.path.dtos.SubscribedPathDto;
import com.opencourse.path.dtos.SubscriptionRequestDto;
import com.opencourse.path.dtos.mappers.PathMapper;
import com.opencourse.path.entities.Project;
import com.opencourse.path.entities.Subscription;
import com.opencourse.path.repos.SubscriptionRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepo subscriptionRepo;
    private final PathMapper pathMapper;

    //only other services
    public Boolean userHasAccessToCourse(Long userId,Long courseId){
        List<Subscription> subs=subscriptionRepo.findByUserId(userId);
        
        if(subs==null)
        return false;

        //for all user subscriptions
        for(int i=0;i<subs.size();i++){
            Subscription sub=subs.get(i);
            //if sub is now activ
            if(
                sub.getDate().isBefore(LocalDateTime.now())
                &&
                sub.getDate().plusMonths(sub.getType().getDurationMonth()).isAfter(LocalDateTime.now())
            ){
                List<Project> projects=sub.getPath().getProjects();
                //for all projects
                for(int j=0;j<projects.size();j++){
                    //for all courses
                    List<Long> courses=projects.get(j).getCourseIds();
                    for(int k=0;k<courses.size();k++){
                        if(courses.get(k).equals(courseId))
                        return true;
                    }
                } 
            }
            
        }
        return false;
    }

    //all users
    public List<SubscribedPathDto> getPathsByUserSub(Long userId){

        //get subscriptions
        List<Subscription> subs=subscriptionRepo.findByUserId(userId);
        List<SubscribedPathDto> subPathsDto=new ArrayList<SubscribedPathDto>();
        //filter (only current subscriptions)
        subs.stream()
        .forEach((sub)->{
            //populater paths list
            SubscribedPathDto subPathDto= new SubscribedPathDto();
            subPathDto.setPathDto(pathMapper.toDto(sub.getPath()));
            subPathDto.setEndsAt(sub.getDate().plusMonths(sub.getType().getDurationMonth()));
            //if subscription is active
            if( 
                sub.getDate().isBefore(LocalDateTime.now()) 
                && 
                sub.getDate().plusMonths(sub.getType().getDurationMonth()).isAfter(LocalDateTime.now())
                )
            subPathDto.setActiveSubscription(true);
            else //if not active
            subPathDto.setActiveSubscription(false);

            subPathsDto.add(subPathDto);
        });

        return subPathsDto;
    }

    //uathentic users
    public void subscribe(SubscriptionRequestDto subReqDto,Long userId){
        
    }
    
}
