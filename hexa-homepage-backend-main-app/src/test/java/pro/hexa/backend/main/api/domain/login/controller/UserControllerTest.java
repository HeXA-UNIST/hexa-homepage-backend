package pro.hexa.backend.main.api.domain.login.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pro.hexa.backend.config.StringCryptoConverter;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto1;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto2;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto3;
import pro.hexa.backend.main.api.domain.login.dto.UserFindVerificationRequestDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringCryptoConverter stringCryptoConverter;

    @Test
    void userSignup() {
        //given
        UserCreateRequestDto request = new UserCreateRequestDto(
            "testId",
            "test@hexa.pro",
            0,
            0,
            "2021",
            "",
            "test",
            "qwer1234",
            "qwer1234"
        );

        //when
        userController.userSignup(request);

        //then
        User user = userRepository.findByNameAndEmail("test", "test@hexa.pro")
            .orElseGet(() -> User.create(null, null, null, null, null, null, null, null, null));
        assertThat(request.getId()).isEqualTo(user.getId());
        assertThat(request.getGender()).isEqualTo(user.getGender().getApiValue());
        assertThat(request.getState()).isEqualTo(user.getState().getApiValue());
        assertThat(request.getRegYear()).isEqualTo(user.getRegYear().toString());
        assertThat(request.getRegNum()).isEqualTo(user.getRegNum());
    }

    @Test
    void findUserIdSendVerificationCode() {
        //given
        UserCreateRequestDto request1 = new UserCreateRequestDto(
                "testId",
                "test@hexa.pro",
                0,
                0,
                "2021",
                "",
                "test",
                "qwer1234",
                "qwer1234"
        );

        //when
        userController.userSignup(request1);

        UserFindIdRequestDto request = new UserFindIdRequestDto(
                "test",
                "test@hexa.pro"
        );

        userController.findUserIdSendVerificationCode(request);

        // then
        User user = userRepository.findByNameAndEmail("test", "test@hexa.pro")
                .orElseGet(() -> User.create(null, null, null, null, null, null, null, null, null));
        assertNotNull(user);
        assertNotNull(user.getVerificationCode());

    }

    @Test
    void idVerifyVerificationCode() {

        UserCreateRequestDto request1 = new UserCreateRequestDto(
                "testId",
                "test@hexa.pro",
                0,
                0,
                "2021",
                "",
                "test",
                "qwer1234",
                "qwer1234"
        );

        //when
        userController.userSignup(request1);

        UserFindIdRequestDto request2 = new UserFindIdRequestDto(
                "test",
                "test@hexa.pro"
        );
        userController.findUserIdSendVerificationCode(request2);

        User user = userRepository.findByNameAndEmail("test", "test@hexa.pro")
                .orElseGet(() -> User.create(null, null, null, null, null, null, null, null, null));


        UserFindVerificationRequestDto request3 = new UserFindVerificationRequestDto(
                "test",
                "test@hexa.pro",
                user.getVerificationCode()
        );

        String k = String.valueOf(userController.idVerifyVerificationCode(request3));
        System.out.println(k);
        assertNotNull(k);


    }

    @Test
    void findUserPasswordById() {

        UserCreateRequestDto request1 = new UserCreateRequestDto(
                "testId",
                "test@hexa.pro",
                0,
                0,
                "2021",
                "",
                "test",
                "qwer1234",
                "qwer1234"
        );

        //when
        userController.userSignup(request1);

        User user = userRepository.findByNameAndEmail("test", "test@hexa.pro")
                .orElseGet(() -> User.create(null, null, null, null, null, null, null, null, null));


        UserFindPasswordRequestDto1 request = new UserFindPasswordRequestDto1("testId");

        ResponseEntity<String> response =userController.findUserPasswordById(request);
        String responseBody = response.getBody();

        assertEquals(responseBody, user.getId());
    }

    @Test
    void passwordVerifyVerificationCode() {
        UserCreateRequestDto request1 = new UserCreateRequestDto(
                "testId",
                "test@hexa.pro",
                0,
                0,
                "2021",
                "",
                "test",
                "qwer1234",
                "qwer1234"
        );

        //when
        userController.userSignup(request1);

        UserFindIdRequestDto request2 = new UserFindIdRequestDto(
                "test",
                "test@hexa.pro"
        );
        userController.findUserPasswordSendVerificationCode(request2);

        User user = userRepository.findByNameAndEmail("test", "test@hexa.pro")
                .orElseGet(() -> User.create(null, null, null, null, null, null, null, null, null));

        UserFindPasswordRequestDto2 request3 = new UserFindPasswordRequestDto2(
                "testId",
                user.getVerificationCode()
        );

        assertNotNull(userController.passwordVerifyVerificationCode(request3));

    }

    @Test
    void changingUserPassword() {

        UserCreateRequestDto request1 = new UserCreateRequestDto(
                "testId",
                "test@hexa.pro",
                0,
                0,
                "2021",
                "",
                "test",
                "qwer1234",
                "qwer1234"
        );

        //when
        userController.userSignup(request1);

        UserFindIdRequestDto request2 = new UserFindIdRequestDto(
                "test",
                "test@hexa.pro"
        );
        userController.findUserPasswordSendVerificationCode(request2);

        User user = userRepository.findByNameAndEmail("test", "test@hexa.pro")
                .orElseGet(() -> User.create(null, null, null, null, null, null, null, null, null));

        UserFindPasswordRequestDto2 request3 = new UserFindPasswordRequestDto2(
                "testId",
                user.getVerificationCode()
        );

        ResponseEntity<String> k = userController.passwordVerifyVerificationCode(request3);
        String token = k.getBody();

        String newPassword = "password1";
        UserFindPasswordRequestDto3 request4 = new UserFindPasswordRequestDto3(newPassword, newPassword);

        userController.changingUserPassword(request4, token);

        // then
        String encryptedPassword = stringCryptoConverter.convertToDatabaseColumn(newPassword);
        assertEquals(user.getPassword(), encryptedPassword);
    }
}
