package com.opencourse.path.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.opencourse.path.exceptions.ElementNotFoundException;
import com.opencourse.path.exceptions.PathActivationException;
import com.opencourse.path.exceptions.PathNotFoundException;
import com.opencourse.path.exceptions.ProjectNotFoundException;
import com.opencourse.path.exceptions.TopicNotFoundException;
import com.opencourse.path.exceptions.UnAuthorizedActionException;
import com.opencourse.path.exceptions.UserNotSubscribedException;

import lombok.Data;

@RestControllerAdvice
public class CustomExceptioHandler {

    @ExceptionHandler({ElementNotFoundException.class})
    public ResponseEntity<ApiError> handleElementNotFoundException(ElementNotFoundException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrors(new ArrayList<String>());
        error.getErrors().add(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<ApiError>(error, error.getStatus());
    }
    
    @ExceptionHandler({PathActivationException.class})
    public ResponseEntity<ApiError> handlePathActivationException(PathActivationException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrors(new ArrayList<String>());
        error.getErrors().add(ex.getMessage());
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<ApiError>(error, error.getStatus());
    }

    @ExceptionHandler({PathNotFoundException.class})
    public ResponseEntity<ApiError> handlePathNotFoundException(PathNotFoundException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrors(new ArrayList<String>());
        error.getErrors().add(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<ApiError>(error, error.getStatus());
    }

    @ExceptionHandler({ProjectNotFoundException.class})
    public ResponseEntity<ApiError> handleProjectNotFoundException(ProjectNotFoundException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrors(new ArrayList<String>());
        error.getErrors().add(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<ApiError>(error, error.getStatus());
    }

    @ExceptionHandler({TopicNotFoundException.class})
    public ResponseEntity<ApiError> handleTopicNotFoundException(TopicNotFoundException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrors(new ArrayList<String>());
        error.getErrors().add(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<ApiError>(error, error.getStatus());
    }

    @ExceptionHandler({UnAuthorizedActionException.class})
    public ResponseEntity<ApiError> handleUnAuthorizedActionException(UnAuthorizedActionException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrors(new ArrayList<String>());
        error.getErrors().add(ex.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<ApiError>(error, error.getStatus());
    }

    @ExceptionHandler({UserNotSubscribedException.class})
    public ResponseEntity<ApiError> handleUserNotSubscribedException(UserNotSubscribedException ex,WebRequest request){
        ApiError error=new ApiError();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrors(new ArrayList<String>());
        error.getErrors().add(ex.getMessage());
        error.setStatus(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<ApiError>(error, error.getStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex,WebRequest request){
        List<String> errors=new ArrayList<String>();
        for(FieldError error:ex.getBindingResult().getFieldErrors()){
            errors.add(error.getDefaultMessage());
        }
        ApiError apiError=new ApiError();
        apiError.setErrors(errors);
        apiError.setMessage(ex.getLocalizedMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(apiError,apiError.getStatus());
    }
    
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + 
            violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ApiError apiError = new ApiError();
        apiError.setErrors(errors);
        apiError.setMessage(ex.getLocalizedMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    
}

@Data
class ApiError{
    private HttpStatus status;
    private String message;
    private List<String> errors;
}