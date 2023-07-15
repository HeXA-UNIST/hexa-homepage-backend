package pro.hexa.backend.main.api.domain.login.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.AuthenticationNumber.domain.AuthenticationNumber;
import pro.hexa.backend.domain.AuthenticationNumber.repository.AuthenticationNumberRepository;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.dto.EmailRequestDto;
import pro.hexa.backend.main.api.common.auth.domain.RefreshToken;
import pro.hexa.backend.main.api.common.auth.repository.RefreshTokenRedisRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.common.exception.DataNotFoundException;
import pro.hexa.backend.main.api.common.jwt.Jwt;
import pro.hexa.backend.main.api.domain.login.dto.EmailAuthenticationRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.EmailAuthenticationTokenDto;
import pro.hexa.backend.main.api.domain.login.dto.EmailSendingRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordWithIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserPasswordChangeRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final AuthenticationNumberRepository authenticationNumberRepository;

    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;


    @Transactional
    public String userSignup(UserCreateRequestDto request) {
        boolean userExists = userRepository.existsById(request.getId());
        //////////// 요청이 유효한지에 대해 판단하는 부분 //////////////
        if (userExists) {

            throw new BadRequestException(BadRequestType.EXISTS_USER);
        }

        if (!request.getPassword1().equals(request.getPassword2())) {
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }
        //////////////////////////////////////////////////
        // 만약 유효한 경우,
        GENDER_TYPE genderType = GENDER_TYPE.findKeyBYApiValue(request.getGender());
        STATE_TYPE stateType = STATE_TYPE.findKeyBYApiValue(request.getState());
        short regYear = Short.parseShort(request.getRegYear()); // String으로 입력받은 등록 날짜를 short로 변환
        User user = User.create(request.getId(), request.getEmail(), genderType,
                stateType, regYear, request.getRegNum(),
                request.getName(), passwordEncoder.encode(request.getPassword1()));

        userRepository.save(user);

        return user.getId();

    }

    public void usernameAndEmailValidationCheck(String name, String email) {
        if (name.equals("")) {
            throw new BadRequestException(BadRequestType.EMPTY_NAME);
        }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new BadRequestException(BadRequestType.INVALID_EMAIL);
        }
    }

    @Transactional
    public EmailRequestDto makeEmailRequestDto(EmailSendingRequestDto requestDto) throws DataNotFoundException {
        usernameAndEmailValidationCheck(requestDto.getName(), requestDto.getEmail());
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime beforeLifeTimeFromNowDateTime = nowDateTime.minusMinutes(AuthenticationNumber.LifeTimeOfAuthenticationNumber);
        // 현재 시각 기준 10분이내에 생성된 인증번호 객체를 모두 가져온다.
        List<AuthenticationNumber> authenticationNumbers = authenticationNumberRepository.findAllByCreatedAt(beforeLifeTimeFromNowDateTime, nowDateTime);

        List<String> authKeys = authenticationNumbers.stream().map(AuthenticationNumber::getRandomAuthenticationNumbers).collect(Collectors.toList());

        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(888888) + 111111);
        // 생성된 인증번호가 10분내로 생성이 되었었다면 다시 생성한다.
        while (authKeys.contains(authKey)) {
            authKey = String.valueOf(random.nextInt(888888) + 111111);
        }

        Optional<User> user = userRepository.findByNameAndEmail(requestDto.getName(), requestDto.getEmail());
        user.orElseThrow(DataNotFoundException::new);


//        String createdAt = DateTimeUtils.toFormat(nowDateTime, DateTimeUtils.YYYY_MM_DD_HH_MM_ss_SSS);

        AuthenticationNumber authenticationNumber = new AuthenticationNumber(nowDateTime, authKey, user.get());
        authenticationNumberRepository.save(authenticationNumber);

        return EmailRequestDto.builder()
                .sendTo(requestDto.getEmail())
                .name(requestDto.getName())
                .content(authKey)
                .build();

    }

    @Transactional
    public EmailAuthenticationTokenDto emailAuthenticationWithNameAndEmail(EmailAuthenticationRequestDto request) throws DataNotFoundException {
        // validation check
        usernameAndEmailValidationCheck(request.getName(), request.getEmail());

        String userEmail = request.getEmail();
        String userName = request.getName();
        String userAuthenticationNumbers = request.getAuthenticationNumbers();


        Optional<User> foundUser = userRepository.findByNameAndEmail(userName, userEmail);
        foundUser.orElseThrow(DataNotFoundException::new);

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime beforeLifeTimeFromNowDateTime = nowDateTime.minusMinutes(AuthenticationNumber.LifeTimeOfAuthenticationNumber);
        // 아래 쿼리를 날려서 현재 시각으로 부터 10분이내 & 유저가 동일한 인증번호 객체를 가져온다.
        Optional<AuthenticationNumber> foundAuthenticationNumber = authenticationNumberRepository.findByUserAndCreatedAt(foundUser.get(), beforeLifeTimeFromNowDateTime, nowDateTime);
        foundAuthenticationNumber.orElseThrow(DataNotFoundException::new);
        if (!foundAuthenticationNumber.get().getRandomAuthenticationNumbers().equals(userAuthenticationNumbers)) {
            throw new BadRequestException(BadRequestType.NOT_MATCH_AUTHENTICATION_NUMBERS);
        }


        // 돌려줄 때 jwt를 발급해서 response에 끼워서 보내줘야함.
        // 그러면 다음 통신때는 request에 jwt를 끼워서 보내주기 때문에
        // validation check을 해줄 필요 없이 그냥 jwt만 decoding해서 맞는지 확인만 해주면 된다.
        String accessToken = Jwt.generateAccessToken(foundUser.get().getId());
        String refreshToken = Jwt.generateRefreshToken();
        RefreshToken refreshTokenEntity = RefreshToken.create(accessToken, refreshToken);
        // repository에 직접적으로 접근해 create(save)를 수행하기 때문에 Transactional annotation을 추가
        refreshTokenRedisRepository.save(refreshTokenEntity);

        return new EmailAuthenticationTokenDto(accessToken, refreshToken);
    }


    public String findUserPasswordWithId(UserFindPasswordWithIdRequestDto request) throws DataNotFoundException {
        String userId = request.getId();
        // validation check
        if (userId == null) {
            throw new BadRequestException(BadRequestType.INVALID_ID);
        }
        // accessing DB
        userRepository.findById(userId).orElseThrow(DataNotFoundException::new);
        return userId;
    }

    @Transactional
    public String validateJwtToken(EmailAuthenticationTokenDto emailAuthenticationTokenDto) {
        // 클레임을 통해서 Jwt validate를 한다.
        Claims claims = Jwt.validate(emailAuthenticationTokenDto.getAccessToken(), Jwt.jwtSecretKey);
        return (String) claims.get(Jwt.JWT_USER_ID);
    }

    @Transactional
    public Void changePassword(UserPasswordChangeRequestDto request, String userId) {

        Void v = null;
        // validation check
        if (request.getPassword_1() == null || request.getPassword_2() == null) {
            throw new BadRequestException(BadRequestType.INVALID_PASSWORD);
        }
        if (!request.getPassword_1().equals(request.getPassword_2())) {
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }
        // change password
        userRepository.getById(userId).setPassword(request.getPassword_1());
        return v;
    }


}
