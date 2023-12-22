package com.crud.simple.loggers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.crud.simple.services.loggingService;

import io.swagger.models.HttpMethod;


@Component
public class logInterceptor implements HandlerInterceptor {
    
    @Autowired
    private loggingService logging_service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals(HttpMethod.GET.name())
                || request.getMethod().equals(HttpMethod.DELETE.name())
                || request.getMethod().equals(HttpMethod.PUT.name()))    {
            logging_service.displayReq(request,null);
        }
        return true;
    }

}
