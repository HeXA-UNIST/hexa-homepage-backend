package pro.hexa.backend.main.api.domain.seminar.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pro.hexa.backend.domain.seminar.domain.Seminar;
import pro.hexa.backend.domain.seminar.repository.SeminarRepository;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.AUTHORIZATION_TYPE;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.domain.seminar.dto.SeminarListResponse;

@SpringBootTest
class SeminarPageControllerTest {

    @Autowired
    private SeminarPageController seminarPageController;

    @Autowired
    private SeminarRepository seminarRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getSeminarListResponse() {
        // given
        User user1 = User.create(
                "user",
                "user",
                GENDER_TYPE.남,
                STATE_TYPE.재학,
                (short) 2020,
                "20202020",
                "User",
                "password",
                AUTHORIZATION_TYPE.Member
        );

        user1 = userRepository.save(user1);  // User 객체 저장

        Seminar seminar1 = Seminar.create(
                LocalDateTime.of(2023,1,1,1,1),
                user1,
                new ArrayList<>()
        );

        Seminar seminar2 = Seminar.create(
                LocalDateTime.of(2024,1,1,1,1),
                user1,
                new ArrayList<>()
        );

        Seminar seminar3 = Seminar.create(
                LocalDateTime.of(2023,1,2,1,1),
                user1,
                new ArrayList<>()
        );

        seminarRepository.save(seminar1);
        seminarRepository.save(seminar2);
        seminarRepository.save(seminar3);

        // when
        ResponseEntity<SeminarListResponse> response = seminarPageController.getSeminarListResponse("", 2023, 3,3);
    }
}
