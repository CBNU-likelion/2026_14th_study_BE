package com.likelion.backend.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ArticleLoggingAspect {

    @Around("execution(* com.likelion.backend.api.article.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long executionTime = end - start;

        String methodName = joinPoint.getSignature().getName();
        log.info("[Article] {} - {}ms", methodName, executionTime);

        return result;
    }
}