package com.example.demo.utilities.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.token", ignoreUnknownFields = false)
public class TokenProperty {

    /**
     * An auto-generated unique string for authentication purpose
     */
    private String raw_content;

    /**
     * Default lifetime for token, count by milliseconds (ms)
     */
    private long tokenLifetime;

    /**
     * Default lifetime for refreshing token, count by milliseconds (ms)
     */
    private long refreshLifetime;
}
