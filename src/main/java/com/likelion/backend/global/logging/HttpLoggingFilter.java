package com.likelion.backend.global.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class HttpLoggingFilter extends OncePerRequestFilter {
    private final HttpLoggingProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!properties.isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }
        String method = request.getMethod();
        String queryString = request.getQueryString();
        String uri = request.getRequestURI() + (queryString != null ? "?" + queryString : "");
        log.info("HTTP Request Started - method={}, uri={}", method, uri);
        try {
            filterChain.doFilter(request, response);
        } finally {
            log.info("HTTP Request Completed - method={}, uri={}, status={}", method, uri, response.getStatus());
        }
    }
}
