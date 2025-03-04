package com.board.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of( "jiin"); // TODO: 임의 데이터로 추후에 바꿔야함. 스프링 시큐리티로 인증 기능을 붙이게 될 때 수정
    }
}
