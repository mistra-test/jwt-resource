package com.example.jwtresource;

import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;
import io.jsonwebtoken.impl.crypto.MacProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeHint;

@TypeHint(types = MacProvider.class)
@TypeHint(types = DefaultJwtBuilder.class)
@TypeHint(types = DefaultJwtParserBuilder.class)
@SpringBootApplication
public class JwtResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtResourceApplication.class, args);
    }
}
