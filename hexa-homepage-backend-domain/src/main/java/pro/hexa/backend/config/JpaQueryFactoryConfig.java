package pro.hexa.backend.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaQueryFactoryConfig {
    @Bean
    public JPAQueryFactory queryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
