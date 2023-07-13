package pro.hexa.backend.domain.emailVerificationCode.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.emailVerificationCode.domain.EmailVerificationCode;

@RequiredArgsConstructor
public class EmailVerificationCodeRepositoryImpl implements EmailVerificationCodeRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public Optional<EmailVerificationCode> findTokenByNameAndEmail(String name, String email) {
        //hmm findIdAuthenticationCode;
        return Optional.ofNullable(
            queryFactory.selectFrom(findIdAuthenticationCode)
                .where(findIdAuthenticationCode.name.eq(name)
                    , findIdAuthenticationCode.email.eq(email))
                .fetchOne()
        );
    }


}
