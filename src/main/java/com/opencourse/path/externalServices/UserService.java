package com.opencourse.path.externalServices;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.opencourse.path.dtos.UserInfoDto;
import com.opencourse.path.prop.ExternalServicesProp;

@Service
public class UserService {
    
    private final ExternalServicesProp prop;
    private RestTemplate restTemplate;
    
    public UserService(ExternalServicesProp prop){
        this.prop=prop;
        restTemplate=new RestTemplate();
    }
    
    public Boolean isUserMentor(Long userId,Long mentorId){
        return false;
    }
    
    public UserInfoDto getUserInfo(Long userId){
        UserInfoDto userInfoDto=new UserInfoDto();
        userInfoDto.setDateOfBirth(LocalDateTime.now());
        userInfoDto.setFirstname("Mohamed Amine");
        userInfoDto.setLastname("Ben Abbes");
        return userInfoDto;
    }

}
