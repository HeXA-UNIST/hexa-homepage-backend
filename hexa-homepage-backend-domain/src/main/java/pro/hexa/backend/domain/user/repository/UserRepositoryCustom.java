package pro.hexa.backend.domain.user.repository;

import pro.hexa.backend.domain.user.domain.User;

public interface UserRepositoryCustom {
    User findByNameAndEmail(String name, String email);
}
