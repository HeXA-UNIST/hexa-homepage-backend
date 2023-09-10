package pro.hexa.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.user.domain.QUser;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.AUTHORIZATION_TYPE;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<User> findAdminAll() {
        QUser user = QUser.user;
        return queryFactory.selectFrom(user)
            .where(user.authorization.eq(AUTHORIZATION_TYPE.Admin))
            .fetch();
    }
}
