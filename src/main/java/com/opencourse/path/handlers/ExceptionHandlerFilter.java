package com.opencourse.path.handlers;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencourse.path.exceptions.CustomAuthenticationException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch(CustomAuthenticationException ex){
            ApiError error=new ApiError();
            error.setMessage(ex.getMessage());
            error.setStatus(HttpStatus.UNAUTHORIZED);
            ObjectMapper mapper=new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));
        }
        
    }
        
}
