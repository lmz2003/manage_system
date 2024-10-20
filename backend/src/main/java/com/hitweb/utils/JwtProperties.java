package com.hitweb.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "hitweb.jwt")
public class JwtProperties {
    private String secret;
    private Long expire;
    private String tokenName;
}
