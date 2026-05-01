# 4주차 과제 수행 계획 및 테스크 리스트

## 1. 개요
본 문서는 4주차 요구사항(로깅 및 AOP 적용)을 효율적으로 수행하기 위한 테스크 리스트를 정의합니다. 기존 비즈니스 로직을 최대한 유지하면서 요구사항을 충족하는 것을 목표로 합니다.

## 2. 전체 전략
1.  **HTTP 요청 로깅**: `Filter`를 사용하여 모든 요청의 메서드, URI, 상태 코드를 로깅합니다. `OncePerRequestFilter`를 상속받아 구현하여 요청당 한 번만 수행되도록 보장합니다.
2.  **실행 시간 측정 (AOP)**: `Article` 도메인의 API 실행 시간을 ms 단위로 측정합니다. 우대 사항인 '커스텀 어노테이션' 방식을 적용하여 선택적으로 측정 가능하게 구현합니다.
3.  **예외 로깅 (AOP)**: `Article` 도메인에서 예외 발생 시 어떤 메서드에서 발생했는지 로깅합니다. `@AfterThrowing` 어드바이스를 사용합니다.

---

## 3. 테스크 리스트

### [Phase 1] 준비 및 환경 설정
- [x] SLF4J 및 Logback 설정 확인 (기본적으로 Spring Boot에 포함됨)
- [x] 전역적으로 사용할 로깅 포맷 및 패키지 구조 결정 (`global.logging`)

### [Phase 2] 필수 요구사항 1: HTTP 요청 로깅 (Filter)
- [ ] `com.likelion.backend.global.logging.filter` 패키지 관리
- [ ] `LoggingFilter` 클래스 생성 (`OncePerRequestFilter` 상속)
    - [ ] `doFilterInternal` 메서드에서 요청 메서드, URI 추출
    - [ ] `filterChain.doFilter` 호출 후 응답 상태 코드 로깅
- [ ] `FilterRegistrationBean`을 사용하여 필터 등록 (필요한 경우)

### [Phase 3] 필수 요구사항 2 & 우대 요구사항 2: Article API 실행 시간 측정 (AOP)
- [ ] `com.likelion.backend.global.logging.annotation` 패키지 관리
- [ ] `@LogExecutionTime` 커스텀 어노테이션 정의
- [ ] `com.likelion.backend.global.logging.aspect` 패키지 관리
- [ ] `LoggingAspect` 클래스 생성
    - [ ] `@Around` 어드바이스 구현
    - [ ] 포인트컷을 `@annotation(LogExecutionTime)`으로 설정
    - [ ] StopWatch 등을 활용해 실행 시간 측정 및 로깅
- [ ] `ArticleController` 또는 `ArticleService` 메서드에 `@LogExecutionTime` 적용 (비즈니스 로직 수정 없이 어노테이션만 추가)

### [Phase 4] 우대 요구사항 1: 예외 발생 시 로깅 (AOP)
- [ ] `LoggingAspect`에 예외 로깅 기능 추가
    - [ ] `@AfterThrowing` 어드바이스 구현
    - [ ] 포인트컷을 `com.likelion.backend.api.article` 패키지 내로 설정
    - [ ] 메서드 이름과 예외 타입, 메시지 로깅
- [ ] `GlobalExceptionAdvice`와 역할이 중복되지 않는지 확인 (Advice는 응답 반환, AOP는 로깅)

### [Phase 5] 검증 및 마무리
- [ ] API 호출을 통해 콘솔에 로그가 정상적으로 남는지 확인 (Swagger 활용)
- [ ] `System.out.println` 사용 여부 전수 조사
- [ ] 체크리스트 항목 최종 점검
- [ ] README.md 또는 문서에 Filter 선택 이유 등 작성

---

## 4. 제약 사항 준수 여부 확인
- `System.out.println()` 미사용: SLF4J `@Slf4j` 사용
- 필수 1번: Spring AOP가 아닌 `Filter` 사용
- 필수 2번, 우대 1번: Spring AOP 사용
- 기존 비즈니스 코드 직접 수정 지양: 로직 내부 수정 대신 어노테이션 추가 및 AOP/Filter 적용
