package com.likelion.backend.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class HttpRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String method = request.getMethod();
        String URI = request.getRequestURI();

        log.info("Request[{}][{}]",method,URI);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        int status = response.getStatus();

        log.info("Response[{}][{}][{}]",request.getMethod(),request.getRequestURI(),status);
    }


}
