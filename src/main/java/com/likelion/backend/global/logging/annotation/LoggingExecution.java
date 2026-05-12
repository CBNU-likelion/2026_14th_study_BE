package com.likelion.backend.global.logging.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggingExecution {

    String value() default "";
}
