package com.crud.simple.services;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class loggingService {
    
    Logger logger = LoggerFactory.getLogger("loggingService");


    public void displayReq(HttpServletRequest request, Object body) {
        StringBuilder reqMessage = new StringBuilder();
        Map<String,String> parameters = getParameters(request);
        Map<String, List<String>> headers = Collections.list(request.getHeaderNames()).stream().collect(Collectors.toMap( Function.identity(), h -> Collections.list(request.getHeaders(h))));
        reqMessage.append("REQUEST ");
        reqMessage.append("method = [").append(request.getMethod()).append("] ");
        reqMessage.append(" path = (").append(request.getRequestURI()).append(") ");
        reqMessage.append(" headers = [").append(headers).append("] ");
        if(!parameters.isEmpty()) {
            reqMessage.append(" parameters = [").append(parameters).append("] ");
        }

        if(!Objects.isNull(body)) {
            reqMessage.append(" body = {").append(body).append("}");
        }

        logger.info("log Request: {}", reqMessage);
    }


    public void displayRes(HttpServletRequest request, HttpServletResponse response, Object body) {
        StringBuilder resMessage = new StringBuilder();
        resMessage.append("RESPONSE ");
        resMessage.append(" method = [").append(request.getMethod()).append("]");
        resMessage.append(" path = (").append(request.getRequestURI()).append(") ");
        /*Map<String, List<String>> headers = response.getHeaderNames().stream().collect(Collectors.toMap( Function.identity(), h -> Collections.list(response.getHeaders(h))));
        if(!headers.isEmpty()) {
            resMessage.append(" headers = [").append(headers).append("]");
        }*/
        resMessage.append(" body = [").append(body).append("]");

        logger.info("logResponse: {}",resMessage);
    }


    private Map<String,String> getParameters(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName,paramValue);
        }
        return parameters;
    }

 
}
