package com.likelion.backend.global.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ArticleExecutionTime {
//    @Around("execution(* com.likelion.backend.api.article.controller.ArticleController.*(..))")
    @Around("@annotation(com.likelion.backend.global.logging.MeasureExecutionTime)")
    public Object measureArticleExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        log.info("[Article Execution Time] method:{}, {} ms", joinPoint.getSignature().getName(), executeTime);
        return result;
    }
}
