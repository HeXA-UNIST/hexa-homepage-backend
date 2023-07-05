package pro.hexa.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.user.domain.User;

import static pro.hexa.backend.domain.user.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public User findByNameAndEmail(String requestedName, String requestedEmail){

        return queryFactory
                .selectFrom(user)
                .where(user.name.eq(requestedName).and(user.email.eq(requestedEmail))).
                fetchOne();
    }
}
