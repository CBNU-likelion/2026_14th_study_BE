package com.likelion.backend.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ArticleApiTimeLoggingAspect {

    // 현재 처리 중인 HTTP 요청 정보를 로그에 함께 남기기 위해 사용한다.
    private final HttpServletRequest request;

    // ArticleController에 정의된 모든 API 메서드의 실행 시간을 측정한다.
    @Around("execution(* com.likelion.backend.api.article.controller.ArticleController.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // nanoTime은 시간 차이를 재는 데 적합해 실행 시간 측정에 사용한다.
        long startTime = System.nanoTime();

        try {
            // 원래 호출하려던 Article API 메서드를 실행한다.
            return joinPoint.proceed();
        } finally {
            // 예외가 발생한 느린 Article API도 확인할 수 있도록 항상 실행 시간을 기록한다.
            long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;
            log.info("Article API method={} uri={} handler={} elapsedMs={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    joinPoint.getSignature().getName(),
                    elapsedMs);
        }
    }
}
