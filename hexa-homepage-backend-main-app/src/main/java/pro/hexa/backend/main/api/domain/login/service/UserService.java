package pro.hexa.backend.main.api.domain.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto2;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto1;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto3;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

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

    public String findUserId(UserFindIdRequestDto request) {
        String name = request.getName();
        String email = request.getEmail();

        Optional<User> userOptional = userRepository.findByName(name);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }

        User user = userOptional.get();

        // Generate verification code
        String verificationCode = generateVerificationCode();

        // Send verification code to the user's email
        emailService.sendVerificationCodeByEmail(email, verificationCode);

        // Store the verification code in the user's record
        user.setVerificationCode(verificationCode);
        userRepository.save(user);

        if (!verificationCode.equals(user.getVerificationCode())) {
            throw new BadRequestException(BadRequestType.INCORRECT_VERIFICATION_CODE);
        }

        return user.getId();
    }

    public String findUserPassword1(UserFindPasswordRequestDto1 request) {
        String userid=request.getId();

        Optional<User> userOptional = userRepository.findById(userid);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }

        User user = userOptional.get();

        return user.getId();
    }

    public String findUserPassword2(UserFindPasswordRequestDto2 request) {
        String name = request.getName();
        String email = request.getEmail();

        Optional<User> userOptional = userRepository.findByName(name);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }

        User user = userOptional.get();
        //인증번호 생성
        String verificationCode = generateVerificationCode();
        // 이메일로 인증번호 전송
        emailService.sendVerificationCodeByEmail(email, verificationCode);
        // 인증번호 user에 저장
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        //인증번호 입력받기
        String userVerificationCode=request.getVerificationCode();

        if (!userVerificationCode.equals(user.getVerificationCode())) {
            throw new BadRequestException(BadRequestType.INCORRECT_VERIFICATION_CODE);
        }

        return user.getId();
    }

    public String findUserPassword3(UserFindPasswordRequestDto3 request, String Id) {
        String password1 = request.getPassword1();
        String password2 = request.getPassword2();

        Optional<User> userOptional = userRepository.findById(Id);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }

        User user = userOptional.get();

        if (!password1.equals(password2)) {
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }

        // 비밀번호 변경 로직...
        user.setPassword(passwordEncoder.encode(password1));
        userRepository.save(user);

        return "finish";
    }


    private String generateVerificationCode() {
        // 6자리 난수 생성
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 100000 이상 999999 이하의 난수 생성
        return String.valueOf(code);
    }




}
