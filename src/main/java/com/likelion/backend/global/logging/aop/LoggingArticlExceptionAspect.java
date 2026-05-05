package com.likelion.backend.global.logging.aop;

import com.likelion.backend.global.logging.LoggingProperties;
import com.likelion.backend.global.logging.annotation.LoggingExecution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingArticlExceptionAspect {

    private final LoggingProperties loggingProperties;

    @AfterThrowing(
            pointcut = "@annotation(loggingExecution)",
            throwing = "exception"
    )
    public void logArticleException(
            JoinPoint joinPoint,
            LoggingExecution loggingExecution,
            Throwable exception
    ) {
        if (!loggingProperties.getArticle().isExceptionEnabled()) {
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        log.error(
                "Article Exception - target={}, method={}, description={}, exceptionType={}, message={}",
                signature.getDeclaringType().getSimpleName(),
                signature.getMethod().getName(),
                loggingExecution.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
    }
}