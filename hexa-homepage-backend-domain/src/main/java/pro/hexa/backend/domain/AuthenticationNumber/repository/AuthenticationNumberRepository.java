package pro.hexa.backend.domain.AuthenticationNumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.AuthenticationNumber.domain.AuthenticationNumber;
@Repository
public interface AuthenticationNumberRepository extends JpaRepository<AuthenticationNumber, Long>, AuthenticationNumberRepositoryCustom {
}
