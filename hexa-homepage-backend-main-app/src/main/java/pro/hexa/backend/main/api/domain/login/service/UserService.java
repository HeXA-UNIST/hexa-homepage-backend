package pro.hexa.backend.main.api.domain.login.service;

import io.lettuce.core.cluster.RedisClusterURIUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeType;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.domain.login.dto.*;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import javax.validation.constraints.Email;
import java.util.*;

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

    @Transactional
    public String findUserId(UserFindIdRequestDto request){
        String userEmail = request.getEmail();
        String userName = request.getName();
        String userAuthenticationNumbers = request.getAuthenticationNumbers();
        User foundUser = userRepository.findByNameAndEmail(userName, userEmail);
        if(foundUser == null){
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        /// 인증번호는 어떻게 구현하는지 생각을 좀 해봐야 할 듯
        if(emailSender.getAuthenticationNumbers() != userAuthenticationNumbers){
            throw new BadRequestException(BadRequestType.NOT_MATCH_AUTHENTICATION_NUMBERS);
        }
        return foundUser.getId(); // 모든 예외상황을 고려하여 인증번호까지 일치할 경우, user1(user2여도 됨)의 Id를 리턴해준다.
    }


    @Transactional
    public String findUserPasswordFirst(UserFindPasswordFirstRequestDto request){
        String userId = request.getId();
        Optional<User> foundUser = userRepository.findById(userId);
        if(foundUser.get() == null){
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        return userId;
    }
    @Transactional
    public String findUserPasswordSecond(UserFindPasswordSecondRequestDto request){
        String userEmail = request.getEmail();
        String userName = request.getName();
        String userAuthenticationNumbers = request.getAuthenticationNumbers();
        User foundUser = userRepository.findByNameAndEmail(userName, userEmail);
        if(foundUser == null){
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        // 인증번호 받아서 인증하는 과정
        if(request.getAuthenticationNumbers() != emailSender.getAuthenticationNumbers()){
            throw new BadRequestException(BadRequestType.NOT_MATCH_AUTHENTICATION_NUMBERS);
        }

        return foundUser.getId();
    }

    @Transactional
    public boolean changePassword(UserFindPasswordThirdRequestDto request){

        if(request.getPassword_1() != request.getPassword_2()){
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }
        User deletedUser = userRepository.getById(request.getId());
        User createdUser = User.create(deletedUser.getId(), deletedUser.getEmail(), deletedUser.getGender(), deletedUser.getState(),
                deletedUser.getRegYear(), deletedUser.getRegNum(), deletedUser.getName(),
                passwordEncoder.encode(request.getPassword_1())); // 비밀번호를 새롭게 쓴 유저
        userRepository.delete(deletedUser);
        return true;
    }






}
