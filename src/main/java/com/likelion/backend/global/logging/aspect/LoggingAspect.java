package com.likelion.backend.global.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(com.likelion.backend.global.logging.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        String methodName = joinPoint.getSignature().toShortString();
        log.info("[PERFORMANCE] {} executed in {}ms", methodName, stopWatch.getTotalTimeMillis());

        return proceed;
    }

    @AfterThrowing(pointcut = "execution(* com.likelion.backend.api.article..*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("[EXCEPTION] {} - Type: {}, Message: {}",
                methodName, ex.getClass().getSimpleName(), ex.getMessage());
    }
}
