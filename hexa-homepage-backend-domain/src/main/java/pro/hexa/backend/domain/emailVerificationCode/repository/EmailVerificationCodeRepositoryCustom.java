package pro.hexa.backend.domain.emailVerificationCode.repository;

import java.util.Optional;
import pro.hexa.backend.domain.emailVerificationCode.domain.EmailVerificationCode;

public interface EmailVerificationCodeRepositoryCustom {

    public Optional<EmailVerificationCode> findTokenByNameAndEmail(String name, String email);

}
