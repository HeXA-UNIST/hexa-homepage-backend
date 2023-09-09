package pro.hexa.backend.main.api.domain.login.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

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
            .orElseGet(() -> User.create(null, null, null, null, null, null, null, null));
        assertThat(request.getId()).isEqualTo(user.getId());
        assertThat(request.getGender()).isEqualTo(user.getGender().getApiValue());
        assertThat(request.getState()).isEqualTo(user.getState().getApiValue());
        assertThat(request.getRegYear()).isEqualTo(user.getRegYear().toString());
        assertThat(request.getRegNum()).isEqualTo(user.getRegNum());
    }

    @Test
    void findUserIdSendVerificationCode() {
    }

    @Test
    void idVerifyVerificationCode() {
    }

    @Test
    void findUserPasswordById() {
    }

    @Test
    void findUserPasswordSendVerificationCode() {
    }

    @Test
    void passwordVerifyVerificationCode() {
    }

    @Test
    void changingUserPassword() {
    }
}
