package pro.hexa.backend.domain.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;

class UserRepositoryTest {

    @InjectMocks
    UserRepositoryImpl userRepository;

    @Mock
    private JPAQueryFactory queryFactory;

/*
 테스트 할 것들
 1. 유저 생성 잘 되는지: create()
 2. 비번 바꾸기 잘 되는지: changePassword(String newPassword)
 3. 인증 코드 잘 설정 되는지: setVerificationCode(String verificationCode)
 */
    private User user1;

    @BeforeEach
    void beforeEach(){
        user1 = User.create(
            "user1",
            "user1@eaxmple.com",
            GENDER_TYPE.남,
            STATE_TYPE.재학,
            (short) 2023,
            "20231111",
            "김선욱",
            "mangp001" //실제 저장할 때는 해쉬 하고서 저장하겠지?
        );
    }

    @Test
    @DisplayName("유저 생성")
    void createUser() {

        Assertions.assertEquals(user1.getId(), "user1");
        Assertions.assertEquals(user1.getEmail(), "user1@eaxmple.com");
        Assertions.assertEquals(user1.getGender(), GENDER_TYPE.남);
        Assertions.assertEquals(user1.getState(), STATE_TYPE.재학);
        Assertions.assertEquals(user1.getRegYear(), (short) 2023);
        Assertions.assertEquals(user1.getRegNum(), "20231111");
        Assertions.assertEquals(user1.getName(), "김선욱");
        Assertions.assertEquals(user1.getPassword(), "mangp001");


    }

    @Test
    @DisplayName("사용자 비밀번호가 제대로 바뀌는지 확인")
    void changePassword() {

        //when
        user1.changePassword("manga123");

        //then
        Assertions.assertEquals(user1.getPassword(), "manga123");
    }

    @Test
    @DisplayName("인증 코드 설정 체크")
    void setVerificationCode() {
        //when
        user1.setVerificationCode("123412");

        //then
        Assertions.assertEquals(user1.getVerificationCode(), "123412");
    }

}