package com.opencourse.path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "path")
@Component
@Getter
@Setter
public class PathProperties {
    private int minNumProject;
    private int minNumElement;
    private int pageSize;
}
