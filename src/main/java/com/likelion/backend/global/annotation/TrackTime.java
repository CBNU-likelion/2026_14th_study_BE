package com.likelion.backend.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메서드 위에 붙일 수 있게 설정
@Retention(RetentionPolicy.RUNTIME) // 실행 중(Runtime)에도 어노테이션 정보를 참조할 수 있게 설정
public @interface TrackTime {
}