package pro.hexa.backend.main.api.domain.login.service;

import io.jsonwebtoken.Claims;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.dto.EmailRequestDto;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.common.jwt.Jwt;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto1;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto2;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto3;
import pro.hexa.backend.main.api.domain.login.dto.UserFindVerificationRequestDto;
import pro.hexa.backend.service.EmailService;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    
    @Transactional
    public String signupUser(UserCreateRequestDto request) {
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
    public void findUserIdSendVerificationCode(UserFindIdRequestDto request) {
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
        EmailRequestDto emailRequestDto = EmailRequestDto.builder()
            .sendTo(email)
            .Subject("Verification Code")
            .Text("Your verification code is: " + verificationCode)
            .build();
        emailService.send(emailRequestDto);

        // Store the verification code in the user's record
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
    }

    public String verifyId(UserFindVerificationRequestDto request) {
        String name = request.getName();
        String email = request.getEmail();
        String userVerificationCode = request.getVerificationCode();

        User user = userRepository.findByNameAndEmail(name, email)
            .orElseThrow(() -> new BadRequestException(BadRequestType.CANNOT_FIND_USER));

        if (!userVerificationCode.equals(user.getVerificationCode())) {
            throw new BadRequestException(BadRequestType.INCORRECT_VERIFICATION_CODE);
        }
        String userId = user.getId();
        return userId;
    }

    private String maskingId(@NonNull String id) {
        return Arrays.stream(id.split(""))
            .map(ch -> {
                if (ch.charAt(0) % 4 == 0) {
                    return "*";
                } else {
                    return ch;
                }
            }).collect(Collectors.joining());
    }

    public String findUserPasswordById(UserFindPasswordRequestDto1 request) {
        String userid = request.getId();

        Optional<User> userOptional = userRepository.findById(userid);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }

        User user = userOptional.get();

        return user.getId();
    }


    public String verifyPassword(UserFindPasswordRequestDto2 request) {
        String id = request.getId();
        String userVerificationCode = request.getVerificationCode();

        User user = userRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(BadRequestType.CANNOT_FIND_USER));

        if (user.getVerificationCode().isEmpty()) {
            throw new BadRequestException(BadRequestType.NULL_VERIFICATION_CODE);
        }

        if (!userVerificationCode.equals(user.getVerificationCode())) {
            throw new BadRequestException(BadRequestType.INCORRECT_VERIFICATION_CODE);
        }

        String userId = user.getId();
        String accessToken = Jwt.generateAccessToken(userId);

        // 현재 시간과 AccessToken의 만료 시간 비교
        Timestamp expiration = Timestamp.valueOf(LocalDateTime.now().plusMinutes(Jwt.ACCESS_TOKEN_EXPIRE_MINUTE));
        Date now = new Date();
        if (now.after(expiration)) {
            return Jwt.generateRefreshToken();
        }

        return accessToken;
    }

    @Transactional
    public String changingUserPassword(UserFindPasswordRequestDto3 request, String token) {
        String password1 = request.getPassword1();
        String password2 = request.getPassword2();

        // 토큰 검증
        Claims claims = Jwt.validate(token, Jwt.jwtSecretKey);
        String userId = claims.get(Jwt.JWT_USER_ID, String.class);

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }

        User user = userOptional.get();

        if (!password1.equals(password2)) {
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }

        // 비밀번호 변경 로직...
        user.setPassword(passwordEncoder.encode(password1));
        userRepository.flush();

        // JWT 토큰 갱신
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String newJwtToken = Jwt.generateAccessToken(user.getId());
        return newJwtToken;
    }

    private String generateVerificationCode() {
        // 6자리 난수 생성
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(900000) + 100000; // 100000 이상 999999 이하의 난수 생성
        return String.valueOf(code);
    }
}