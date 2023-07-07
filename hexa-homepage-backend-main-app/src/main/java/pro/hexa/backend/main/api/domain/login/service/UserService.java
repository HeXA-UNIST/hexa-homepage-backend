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
import pro.hexa.backend.main.api.common.exception.DataNotFoundException;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordWithIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserPasswordChangeRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.emailAuthenticationRequestDto;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailSender emailSender;

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


    public String emailAuthenticationWithNameAndEmail(emailAuthenticationRequestDto request) throws DataNotFoundException {


        String userEmail = request.getEmail();
        String userName = request.getName();
        String userAuthenticationNumbers = request.getAuthenticationNumbers();
        // for email validation check, make the variable regex, pattern, matcher.
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userEmail);

        // email Authentication
        if (emailSender.getAuthenticationNumbers() != userAuthenticationNumbers) {
            throw new BadRequestException(BadRequestType.NOT_MATCH_AUTHENTICATION_NUMBERS);
        }

        // validation check
        if (userEmail == null || !(matcher.matches())) {
            throw new BadRequestException(BadRequestType.INVALID_EMAIL);
        }
        if (userName == null ) {
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        Optional<User> foundUser = userRepository.findByNameAndEmail(userName, userEmail);
        foundUser.orElseThrow(() -> new DataNotFoundException());


        return foundUser.get().getId(); // 모든 예외상황을 고려하여 인증번호까지 일치할 경우, user1(user2여도 됨)의 Id를 리턴해준다.
    }


    public String findUserPasswordWithId(UserFindPasswordWithIdRequestDto request) throws DataNotFoundException {
        String userId = request.getId();
        // validation check
        if(userId == null){
            throw new BadRequestException(BadRequestType.INVALID_ID);
        }
        // accessing DB
        userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException());
        return userId;
    }


    @Transactional
    public Void changePassword(UserPasswordChangeRequestDto request) {
        Void v = null;
        // validation check
        if (request.getPassword_1() == null || request.getPassword_2() == null) {
            throw new BadRequestException(BadRequestType.INVALID_PASSWORD);
        }
        if (request.getPassword_1() != request.getPassword_2()) {
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }
        // change password
        userRepository.getById(request.getId()).setPassword(request.getPassword_1());
        return v;
    }


}
