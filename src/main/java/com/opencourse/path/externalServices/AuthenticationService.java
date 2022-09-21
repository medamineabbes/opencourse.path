package com.opencourse.path.externalServices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.opencourse.path.prop.ExternalServicesProp;

@Service
public class AuthenticationService {
    
    private RestTemplate restTemplate;
    private final ExternalServicesProp prop;
    public AuthenticationService(ExternalServicesProp prop){
        restTemplate=new RestTemplate();
        this.prop=prop;
    }

    public Boolean isValid(String token){
        ResponseEntity<Boolean> result=restTemplate.postForEntity(prop.getAuthUrl() + "?token=" + token, null, Boolean.class);
        return result.getBody(); 
    }

}
