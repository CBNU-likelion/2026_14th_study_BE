package com.likelion.backend.global.logging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.logging")
public class LoggingProperties {
    private Http http = new Http();
    private Article article = new Article();

    @Getter
    @Setter
    public static class Http {
        private boolean enabled = true;
    }

    @Getter
    @Setter
    public static class Article {
        private boolean timeEnabled = true;
        private boolean exceptionEnabled = true;
    }
}