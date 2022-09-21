package com.opencourse.path.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "external")
@Data
public class ExternalServicesProp {
    
    private String authUrl;
    private String userUrl;
}
