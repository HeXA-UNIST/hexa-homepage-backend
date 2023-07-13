package pro.hexa.backend.domain.user.repository;

import java.util.Optional;
import pro.hexa.backend.domain.user.domain.User;

public interface UserRepositoryCustom {

    Optional<User> findbyNameAndEmail(String name, String email);
}
