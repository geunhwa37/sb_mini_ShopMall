package org.zerock.sb_mini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//QueryDSL을 사용하기 위한 설정 파일
//주요 역할은 JPAQueryFactory를 스프링 빈으로 등록하는 것.

@Configuration  // 이 클래스가 설정 파일(Configuration)이라는 걸 나타냄
@EnableJpaAuditing //JpaAuditing을 활성화
public class QuerydslConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}

