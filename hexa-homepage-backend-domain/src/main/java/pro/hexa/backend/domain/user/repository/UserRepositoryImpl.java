package pro.hexa.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.user.domain.User;

import java.util.Optional;

import static pro.hexa.backend.domain.user.domain.QUser;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByNameAndEmail(String requestedName, String requestedEmail) {
        QUser user = Quser.user;

        return Optional.ofNullable(queryFactory.selectFrom(user)
                .where(user.name.eq(requestedName),
                        user.email.eq(requestedEmail))
                .fetchOne());
    }
}
