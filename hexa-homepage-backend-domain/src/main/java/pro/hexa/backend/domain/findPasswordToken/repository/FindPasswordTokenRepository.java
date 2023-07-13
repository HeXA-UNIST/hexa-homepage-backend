package pro.hexa.backend.domain.findPasswordToken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindPasswordTokenRepository extends JpaRepository<FindPasswordTokenRepository, String>, FindPasswordTokenRepositoryCustom {

}
