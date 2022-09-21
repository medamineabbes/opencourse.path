package com.opencourse.path.handlers;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;
}
