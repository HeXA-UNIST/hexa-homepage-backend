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
import pro.hexa.backend.main.api.domain.login.dto.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

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
        //////////// 요청이 유효한지에 대해 판단하는 부분 //////////////
        //////////// 만약 이미 User가 있다면 만들면 안되기 때문이구나!//
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
        List<User> listOfUser = userRepository.findAll();
        String requestedUsername = request.getName();
        String requestedUserEmail = request.getEmail();
        boolean nameExists = false;
        boolean emailExists = false;
        boolean match = false;
        User user1 = null;
        User user2 = null;
        for(User user : listOfUser){
            if(user.getName() == requestedUsername){
                nameExists = true;
                user1 = user;
            }
            if(user.getEmail() == requestedUserEmail){
                emailExists = true;
                user2 = user;
            }

        }
        if(!nameExists){
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        if(!emailExists){
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        if(user1 == user2){
            match = true;
        }
        if(!match){
            throw new BadRequestException(BadRequestType.NOT_MATCH_BETWEEN_NAME_AND_EMAIL);
        }
        /// 이름과 이메일이 매치되었을 경우.

        if(!(request.getAuthenticationNumbers() == AuthenticationNumbers)){
            throw new BadRequestException(BadRequestType.NOT_MATCH_AUTHENTICATION_NUMBERS);
        }
        return user1.getId(); // 모든 예외상황을 고려하여 인증번호까지 일치할 경우, user1(user2여도 됨)의 Id를 리턴해준다.
    }


    @Transactional
    public String findUserPasswordFirst(UserFindPasswordFirstRequestDto request){
        List<User> ListOfUser = userRepository.findAll();
        boolean idExists = false;
        String id = "";
        for(User user : ListOfUser){
            if(user.getId() == request.getId()){
                idExists = true;
                id = request.getId();
                break;
            }
        }
        if(!idExists){
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        return id;
    }

    @Transactional
    public String findUserPasswordSecond(UserFindPasswordSecondRequestDto request){
        List<User> ListOfUser = userRepository.findAll();
        String id = findIdByUsername(request.getName());
        if(request.getName() != userRepository.getById(id).getName()){
            throw new BadRequestException(BadRequestType.CANNOT_FIND_USER);
        }
        if(request.getEmail() != userRepository.getById(id).getEmail()){
            throw new BadRequestException(BadRequestType.NOT_MATCH_BETWEEN_NAME_AND_EMAIL);
        }
        if(request.getAuthenticationNumbers() != AuthenticationNumbers){
            throw new BadRequestException(BadRequestType.NOT_MATCH_AUTHENTICATION_NUMBERS);
        }
        return id;
    }
    public String findIdByUsername(String name){
        List<User> listOfUser = userRepository.findAll();
        String id = null;
        for(User user: listOfUser){
            if(user.getName() == name){
                id = user.getId();
                break;
            }
        }
        return id;
    }
    @Transactional
    public String changePassword(UserFindPasswordThirdRequestDto request, String id){

        if(request.getPassword_1() != request.getPassword_2()){
            throw new BadRequestException(BadRequestType.INCORRECT_PASSWORD);
        }
        User deletedUser = userRepository.getById(id);
        User createdUser = User.create(deletedUser.getId(), deletedUser.getEmail(), deletedUser.getGender(), deletedUser.getState(),
                deletedUser.getRegYear(), deletedUser.getRegNum(), deletedUser.getName(),
                passwordEncoder.encode(request.getPassword_1())); // 새롭게 쓰여진 유저
    userRepository.delete(deletedUser);
    return id;
    }





}
