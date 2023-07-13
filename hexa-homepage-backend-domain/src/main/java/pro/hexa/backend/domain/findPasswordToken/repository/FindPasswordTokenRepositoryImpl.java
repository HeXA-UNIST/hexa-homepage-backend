package pro.hexa.backend.domain.findPasswordToken.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.findPasswordToken.domain.FindPasswordToken;
import pro.hexa.backend.domain.user.domain.User;

@RequiredArgsConstructor
public class FindPasswordTokenRepositoryImpl implements FindPasswordTokenRepositoryCustom{
    private JPAQueryFactory queryFactory;

    @Override
    public Optional<FindPasswordToken> findTokenById(String id) {
        //hmm token;
        return Optional.ofNullable(
            queryFactory.selectFrom(token)
                .where(token.id.eq(id))
                .fetchOne()
        );
    }
}
