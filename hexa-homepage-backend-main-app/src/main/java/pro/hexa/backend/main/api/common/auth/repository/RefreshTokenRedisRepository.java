package pro.hexa.backend.main.api.common.auth.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.main.api.common.auth.domain.RefreshToken;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {

    
}
