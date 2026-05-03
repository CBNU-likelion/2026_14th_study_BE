package com.likelion.backend.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect // 이 클래스가 AOP 역할을 한다는 것을 스프링에게 알려줌
@Component // 스프링 빈으로 등록
public class TimeTraceAspect {

    // article 패키지와 그 하위 패키지에 있는 모든 메서드에 적용함
    @Pointcut("execution(* com.likelion.backend.api.article..*(..))")
    private void articleDomainPointcut() {}

    // @Around를 사용하여 대상 메서드의 '실행 전'과 '실행 후' 모두에 개입함
    @Around("articleDomainPointcut()")
    public Object executeTimeTrace(ProceedingJoinPoint joinPoint) throws Throwable { // Throwable : article 로직에서 에러가 발생할 때 밖으로 에러를 던져줌

        // 실행되는 대상 클래스와 메서드 이름을 간략히 가져옴 (로그 가독성을 위해)
        String targetMethodName = joinPoint.getSignature().toShortString();

        // [메서드 실행 전] 시작 시간을 밀리초 단위로 기록
        long startTime = System.currentTimeMillis();

        try {
            // joinPoint.proceed() : 원래 실행하려던 비즈니스 로직(Article 기능)을 실행하라는 명령
            return joinPoint.proceed();
        } finally {
            // [메서드 실행 후] 예외가 발생하든 안 하든 무조건 종료 시간을 기록하고 계산함
            long endTime = System.currentTimeMillis();
            long executionTimeMs = endTime - startTime;

            // 로그 출력
            log.info("[TimeTrace] Method: {} | Execution Time: {} ms", targetMethodName, executionTimeMs);
        }
    }
}