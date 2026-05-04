package com.likelion.backend.global.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ArticleExceptionLogging {
    @AfterThrowing(
            pointcut = "execution(* com.likelion.backend.api.article..*(..))",
            throwing = "ex"
    )
    public void logArticleException(JoinPoint joinPoint, Exception ex) {
        log.error("[Article Exception] method={}, exception={}",
                joinPoint.getSignature().toShortString(),
                ex.getClass().getSimpleName());
    }
}
