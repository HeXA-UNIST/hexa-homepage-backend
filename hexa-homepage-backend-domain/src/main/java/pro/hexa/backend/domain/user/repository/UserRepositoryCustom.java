package pro.hexa.backend.domain.user.repository;

import pro.hexa.backend.domain.user.domain.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByNameAndEmail(String name, String email);
}
