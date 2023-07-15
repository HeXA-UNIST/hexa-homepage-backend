package pro.hexa.backend.domain.AuthenticationNumber.repository;

import pro.hexa.backend.domain.AuthenticationNumber.domain.AuthenticationNumber;
import pro.hexa.backend.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuthenticationNumberRepositoryCustom {
    Optional<AuthenticationNumber> findByAuthKey(String authKey);

    Optional<AuthenticationNumber> findByUserAndCreatedAt(User user, LocalDateTime beforeLifeTimeFromNowDateTime, LocalDateTime nowDateTime);

    List<AuthenticationNumber> findAllByCreatedAt(LocalDateTime beforeLifeTimeFromNowDateTime, LocalDateTime nowDateTime);
}
