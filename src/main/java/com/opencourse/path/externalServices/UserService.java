package com.opencourse.path.externalServices;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.opencourse.path.dtos.UserInfoDto;

@Service
public class UserService {
    
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
