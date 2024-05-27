package com.twoPotatoes.bobJoying.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

@Configuration
public class GraphQlConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        // GraphQL Schema에 Date 타입을 추가합니다.
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Date);
    }
}
