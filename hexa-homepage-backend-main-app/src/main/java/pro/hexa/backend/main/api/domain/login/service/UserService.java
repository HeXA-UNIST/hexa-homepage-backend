package pro.hexa.backend.main.api.domain.login.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.emailVerificationCode.domain.EmailVerificationCode;
import pro.hexa.backend.domain.emailVerificationCode.repository.EmailVerificationCodeRepository;
import pro.hexa.backend.domain.findPasswordToken.domain.FindPasswordToken;
import pro.hexa.backend.domain.findPasswordToken.repository.FindPasswordTokenRepository;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.common.email.EmailServiceImpl;
import pro.hexa.backend.main.api.common.email.TempEmailService;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.domain.login.dto.FindIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.ErrorCheckResponse;
import pro.hexa.backend.main.api.domain.login.dto.FindpwChangePwRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.FindpwRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.FindpwSendCodeRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.FindpwWithCodeResponse;
import pro.hexa.backend.main.api.domain.login.dto.VerifyEmailWithCodeRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.FindIdWithCodeResponse;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailServiceImpl emailService;

    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final FindPasswordTokenRepository findPasswordTokenRepository;


    @Transactional
    public String userSignup(UserCreateRequestDto request) {
        boolean userExists = userRepository.existsById(request.getId());

        if (userExists) {
            throw new BadRequestException(BadRequestType.EXISTS_USER);
        }

        if (!request.getPassword1().equals(request.getPassword2())) {
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }

        GENDER_TYPE genderType = GENDER_TYPE.findKeyBYApiValue(request.getGender());
        STATE_TYPE stateType = STATE_TYPE.findKeyBYApiValue(request.getState());
        short regYear = Short.parseShort(request.getRegYear());

        User user = User.create(request.getId(), request.getEmail(), genderType, stateType,
            regYear, request.getRegNum(), request.getName(), passwordEncoder.encode(request.getPassword1()));

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public ErrorCheckResponse userFindId(FindIdRequestDto request) {

        if (request.getName().equals("") || request.getEmail().equals("")) {
            return ErrorCheckResponse.builder()
                .error(1)
                .build();
        }

        Optional<User> user = userRepository.findbyNameAndEmail(request.getName(), request.getEmail());

        if (user.isEmpty()) {
            return ErrorCheckResponse.builder()
                .error(1)
                .build();
        }

        /*
        TODO: check if there is a code that already generated with same email when generate and save the authentication code.
        */
        EmailVerificationCode emailVerificationCode = EmailVerificationCode.create(request.getName(), request.getEmail());
        String authCode = emailVerificationCode.getCode();

        emailService.sendEmail(request.getEmail(), "blahblah", authCode);

        return ErrorCheckResponse.builder()
            .error(0)
            .build();

    }

    public FindIdWithCodeResponse userFindIdWithCode(VerifyEmailWithCodeRequestDto request) {
        if (request.getName().equals("") || request.getEmail().equals("")) {
            return FindIdWithCodeResponse.builder()
                .error(1)
                .userId("")
                .build();
        }

        Optional<EmailVerificationCode> findIdAuthenticationCode = emailVerificationCodeRepository.findTokenByNameAndEmail(
            request.getName(), request.getEmail());

        if (findIdAuthenticationCode.isEmpty()) {
            return FindIdWithCodeResponse.builder()
                .error(1)
                .userId("")
                .build();
        }

        if (!findIdAuthenticationCode.get().getCode().equals(request.getCode())) {
            return FindIdWithCodeResponse.builder()
                .error(1)
                .userId("")
                .build();
        }

        Optional<User> user = userRepository.findbyNameAndEmail(request.getName(), request.getEmail());

        return FindIdWithCodeResponse.builder()
            .error(0)
            .userId(user.get().getId())
            .build();

    }

    public ErrorCheckResponse userFindPw(FindpwRequestDto request) {
        boolean doesUserExists = userRepository.existsById(request.getId());
        if (!doesUserExists) {
            return ErrorCheckResponse.builder().error(1).build();
        }
        return ErrorCheckResponse.builder().error(0).build()
    }

    @Transactional
    public ErrorCheckResponse userFindPwSendCode(FindpwSendCodeRequestDto request) {
        if (request.getName().equals("") || request.getEmail().equals("") || request.getId().equals("")) {
            return ErrorCheckResponse.builder()
                .error(1)
                .build();
        }

        Optional<User> user = userRepository.findbyNameAndEmail(request.getName(), request.getEmail());

        if (user.isEmpty() || !user.get().getName().equals(request.getName()) || !user.get().getEmail().equals(request.getEmail())) {
            return ErrorCheckResponse.builder()
                .error(1)
                .build();
        }

        /*
        TODO: check if there is a code that already generated with same email when generate and save the authentication code.
        */
        EmailVerificationCode emailVerificationCode = EmailVerificationCode.create(request.getName(), request.getEmail());
        String authCode = emailVerificationCode.getCode();

        emailService.sendEmail(request.getEmail(), "blahblah", authCode);

        return ErrorCheckResponse.builder()
            .error(0)
            .build();
    }

    @Transactional
    public FindpwWithCodeResponse userFindPwWithCode(VerifyEmailWithCodeRequestDto request) {
        if (request.getName().equals("") || request.getEmail().equals("")) {
            return FindpwWithCodeResponse.builder()
                .error(1)
                .token("")
                .build();
        }

        Optional<EmailVerificationCode> findIdAuthenticationCode = emailVerificationCodeRepository.findTokenByNameAndEmail(
            request.getName(), request.getEmail());

        if (findIdAuthenticationCode.isEmpty()) {
            return FindpwWithCodeResponse.builder()
                .error(1)
                .token("")
                .build();
        }

        if (!findIdAuthenticationCode.get().getCode().equals(request.getCode())) {
            return FindpwWithCodeResponse.builder()
                .error(1)
                .token("")
                .build();
        }

        /*
        TODO: check if there is a token that already generated with same Id when generate and save the verification token.
        */
        Optional<User> user = userRepository.findbyNameAndEmail(request.getName(), request.getEmail());
        FindPasswordToken findPasswordToken = FindPasswordToken.create(user.get().getId());

        return FindpwWithCodeResponse.builder()
            .error(0)
            .token(findPasswordToken.getToken())
            .build();
    }

    @Transactional
    public ErrorCheckResponse userFindPwChangePw(FindpwChangePwRequestDto request){
        Optional<FindPasswordToken> findPasswordToken=findPasswordTokenRepository.findTokenById(request.getId());
        if(!findPasswordToken.get().getToken().equals(request.getToken())){
            return ErrorCheckResponse.builder().error(1).build();
        }
        if(!request.getNewPw().equals(request.getNewPwConfirm())){
            return ErrorCheckResponse.builder().error(1).build();
        }
        Optional<User> user = userRepository.findById(request.getId());
        user.get().changePassword(request.getNewPw());

        return ErrorCheckResponse.builder().error(0).build();
    }

}
