package com.twoPotatoes.bobJoying.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity      // Spring Security 지원을 가능하게 함
@EnableMethodSecurity(securedEnabled = true)    // 컨트롤레어 메서드마다 인가를 걸어주기 위함
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Cross-Site Request Forgery(CSRF) 방지 기능 비활성화
        // 활성화시 html에서 csrf 토큰이 포함되어야 요청을 받아들임
        http.csrf(AbstractHttpConfigurer::disable);

        // Spring Security가 세션을 생성하지 않고 보안 컨텍스트를 얻기 위해 세션을 사용하지도 않는다.(STATELESS)
        http.sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 유저는 어플리케이션의 /graphql, /graphiql 요청에 인증하지 않아도 된다. 그 외 요청은 거부
        // 인가는 메서드 단위로 컨트롤러에서 걸어줄 예정
        http.authorizeHttpRequests(authorizeHttpRequests ->
            authorizeHttpRequests
                .requestMatchers("/graphql").permitAll()
                .requestMatchers("/graphiql").permitAll()
                .requestMatchers("/h2-console").permitAll()
                .anyRequest().denyAll()
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
