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
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserLoginRequestDto;

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

    @Transactional
    public boolean userLogin(UserLoginRequestDto request) {
        // 사용자 조회
        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new BadRequestException(BadRequestType.CANNOT_FIND_USER));

        // 사용자 인증 절차 수행
        boolean authenticated = authenticateUser(user, request.getPassword());

        if (authenticated) {
            // 인증 성공
            // 로그인 처리 및 세션 관리 등 추가 작업
            return true;
        } else {
            // 인증 실패
            // 실패에 대한 예외 처리 또는 로그인 실패 처리 등 수행

            return false;
        }
    }

    private boolean authenticateUser(User user, String password) {
        // 사용자 인증 처리 로직 구현
        // 예를 들어, 입력한 비밀번호와 저장된 비밀번호를 비교하여 인증 여부를 확인할 수 있습니다.
        // 실제 인증 과정은 프로젝트의 인증 방식에 따라 달라질 수 있습니다.

        return true; // 인증 성공 시
        // 또는 return false; // 인증 실패 시
    }

}
