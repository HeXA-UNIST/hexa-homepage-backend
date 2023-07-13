package pro.hexa.backend.domain.findPasswordToken.repository;

import java.util.Optional;
import pro.hexa.backend.domain.findPasswordToken.domain.FindPasswordToken;

public interface FindPasswordTokenRepositoryCustom {

    Optional<FindPasswordToken> findTokenById(String id);
}
