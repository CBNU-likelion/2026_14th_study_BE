package com.likelion.backend.global.logging.aop;

import com.likelion.backend.global.logging.LoggingProperties;
import com.likelion.backend.global.logging.annotation.LoggingExecution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingExecutionTimeAspect {

    private final LoggingProperties loggingProperties;

    @Around("@annotation(loggingExecution)")
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint,
            LoggingExecution loggingExecution
    ) throws Throwable {
        if (!loggingProperties.getArticle().isTimeEnabled()) {
            return joinPoint.proceed();
        }
        long startTime = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long elapsedTime = System.nanoTime() - startTime;
            long elapsedMs = elapsedTime / 1_000_000;

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            log.info(
                    "Execution Time - target={}, method={}, description={}, elapsedMs={}",
                    signature.getDeclaringType().getSimpleName(),
                    signature.getMethod().getName(),
                    loggingExecution.value(),
                    elapsedMs
            );
        }
    }
}