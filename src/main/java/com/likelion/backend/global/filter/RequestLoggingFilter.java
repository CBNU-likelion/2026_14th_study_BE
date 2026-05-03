package com.likelion.backend.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j // log.info()를 사용하기 위한 롬복 어노테이션
@Component // 스프링 빈으로 등록하여 필터가 자동으로 적용되게 함
public class RequestLoggingFilter extends OncePerRequestFilter { // OncePerRequestFilter : 한 번의 요청에는 딱 한 번만 실행되는 필터

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청(Request)이 들어올 때의 정보 추출
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 2. 실제 요청 처리 (다음 필터나 Controller로 흐름을 넘김)
        // 이 코드가 실행되는 동안 비즈니스 로직(Article 생성, 조회 등)이 처리됩니다.
        filterChain.doFilter(request, response);

        // 3. 응답(Response)이 나갈 때의 상태 코드 추출 (요청 처리가 모두 끝난 후)
        int status = response.getStatus();

        // 4. 요구사항에 맞게 로그 출력
        log.info("[HTTP Request] Method: {}, URI: {}, Status: {}", method, uri, status);
    }
}