package com.likelion.backend.global.logging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.logging.http")
public class HttpLoggingProperties {
    private boolean enabled = true;
}