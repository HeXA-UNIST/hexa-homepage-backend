package pro.hexa.backend.domain.AuthenticationNumber.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import pro.hexa.backend.domain.AuthenticationNumber.domain.AuthenticationNumber;
import pro.hexa.backend.domain.AuthenticationNumber.domain.QAuthenticationNumber;
import pro.hexa.backend.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AuthenticationNumberRepositoryImpl implements AuthenticationNumberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AuthenticationNumber> findByAuthKey(String authKey) {
        QAuthenticationNumber authenticationNumber = QAuthenticationNumber.authenticationNumber;

        return Optional.ofNullable(queryFactory.selectFrom(authenticationNumber)
                .where(authenticationNumber.randomAuthenticationNumbers.eq(authKey))
                .fetchOne());
    }

    @Override
    public Optional<AuthenticationNumber> findByUserAndCreatedAt(User subject, LocalDateTime beforeLifeTimeFromNowDateTime, LocalDateTime nowDateTime) {
        QAuthenticationNumber authenticationNumber = QAuthenticationNumber.authenticationNumber;

        return Optional.ofNullable(queryFactory.selectFrom(authenticationNumber)
                .where(authenticationNumber.user.eq(subject),
                        authenticationNumber.createdAt.between(beforeLifeTimeFromNowDateTime, nowDateTime)
                )
                .fetchOne());
    }

    @Override
    public List<AuthenticationNumber> findAllByCreatedAt(LocalDateTime beforeLifeTimeFromNowDateTime, LocalDateTime nowDateTime) {
        QAuthenticationNumber authenticationNumber = QAuthenticationNumber.authenticationNumber;
        return queryFactory.selectFrom(authenticationNumber)
                .where((authenticationNumber.createdAt.between(beforeLifeTimeFromNowDateTime, nowDateTime)))
                .fetch();
    }
}
