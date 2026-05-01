# 로깅 구현 계획 (Phase 1)

## 1. 개요
서비스 운영 중 발생하는 장애 추적 및 성능 분석을 위해 로깅 시스템을 구축합니다. SLF4J와 Logback을 기본으로 하며, 비즈니스 코드 수정을 최소화하기 위해 Filter와 Spring AOP를 활용합니다.

## 2. 기술 스택 및 라이브러리
- **Logging Facade**: SLF4J
- **Logging Implementation**: Logback
- **Lombok**: `@Slf4j` 어노테이션 활용
- **Spring AOP**: `spring-boot-starter-aop`

## 3. 로깅 전략

### A. HTTP 요청/응답 로깅 (필수 1)
- **방법**: `Filter` 또는 `Interceptor`
- **선택**: `Filter` (특히 `OncePerRequestFilter`)
    - **이유**: 서블릿 컨테이너 수준에서 요청의 시작과 끝을 가장 정확하게 파악할 수 있으며, Spring MVC context 외부의 문제도 로깅 가능하기 때문입니다.
- **로깅 항목**: HTTP Method, URI, Response Status Code

### B. 실행 시간 측정 및 예외 로깅 (필수 2, 우대 1, 2)
- **방법**: Spring AOP
- **대상**: `Article` 도메인 관련 서비스/컨트롤러 (어노테이션 기반 선택적 적용 가능)
- **커스텀 어노테이션**: `@LogExecutionTime` 정의
- **로깅 항목**: 
    - 메서드 명, 실행 시간 (ms)
    - 예외 발생 시: 메서드 명, 예외 종류 및 메시지

## 4. 패키지 구조 (`global.logging`)
`com.likelion.backend.global.logging` 하위에 기능을 분리하여 관리합니다.

```text
com.likelion.backend.global.logging
├── filter
│   └── LoggingFilter.java          # HTTP 요청/응답 로깅 담당
├── aspect
│   └── LoggingAspect.java          # 실행 시간 및 예외 로깅 담당 (AOP)
└── annotation
    └── LogExecutionTime.java      # 실행 시간 측정용 커스텀 어노테이션
```

## 5. 단계별 실행 계획
1. **준비**: `application.yml`에 기본적인 로깅 레벨 설정 및 패키지 구조 생성.
2. **HTTP 로깅 구현**: `LoggingFilter` 구현 및 등록.
3. **AOP 환경 구축**: `@LogExecutionTime` 어노테이션 및 `LoggingAspect` 기본 틀 생성.
4. **기능 완성**: 실행 시간 측정 및 예외 로깅 로직 구현.
5. **검증**: Log를 통해 데이터가 정확히 남는지 확인 및 기존 코드 수정 여부 체크.
