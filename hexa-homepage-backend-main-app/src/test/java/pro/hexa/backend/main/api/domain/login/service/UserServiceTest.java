package pro.hexa.backend.main.api.domain.login.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.service.EmailService;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;

    @Test
    @DisplayName("정상적인 회원가입 테스트")
    void signupUserNormal() {
        //given
        UserCreateRequestDto userCreateRequestDto = createDefaultUserCreateRequestDtoForTest();

        Mockito.when(userRepository.existsById(userCreateRequestDto.getId()))
            .thenReturn(false);

        String result = userService.signupUser(userCreateRequestDto);

        //when, then
        assertEquals(result, userCreateRequestDto.getId());

    }

    @Test
    void findUserSendVerificationCode() {
    }

    @Test
    void verifyId() {
    }

    @Test
    void findUserPasswordById() {
    }

    @Test
    void verifyPassword() {
    }

    @Test
    void changeUserPassword() {
    }

    UserCreateRequestDto createDefaultUserCreateRequestDtoForTest() {
        UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto();
        userCreateRequestDto.setId("seonuk");
        userCreateRequestDto.setEmail("asd@example.com");
        userCreateRequestDto.setGender(GENDER_TYPE.여.getApiValue());
        userCreateRequestDto.setState(STATE_TYPE.휴학.getApiValue());
        userCreateRequestDto.setRegYear("2020");
        userCreateRequestDto.setRegNum("20202020");
        userCreateRequestDto.setName("김선욱");
        userCreateRequestDto.setPassword1("mangp123");
        userCreateRequestDto.setPassword2("mangp123");

        return userCreateRequestDto;
    }
}