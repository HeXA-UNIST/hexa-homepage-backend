package pro.hexa.backend.domain.emailVerificationCode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.hexa.backend.domain.emailVerificationCode.domain.EmailVerificationCode;

public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, String>,
    EmailVerificationCodeRepositoryCustom {

}
