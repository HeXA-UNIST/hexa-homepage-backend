package pro.hexa.backend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.user.domain.User;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public User findbyNameAndEmail(String name, String email) {
        //hmm users;
        return queryFactory.selectFrom(user)
            .where(user.name.eq(name)
                .and.email.eq(email))
            .fetchOne();
    }
}
