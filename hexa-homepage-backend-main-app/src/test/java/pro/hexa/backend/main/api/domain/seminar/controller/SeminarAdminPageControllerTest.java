package pro.hexa.backend.main.api.domain.seminar.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.bytebuddy.asm.Advice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.hexa.backend.domain.abstract_activity.domain.AbstractActivity;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.attachment.repository.AttachmentRepository;
import pro.hexa.backend.domain.seminar.domain.Seminar;
import pro.hexa.backend.domain.seminar.repository.SeminarRepository;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.AUTHORIZATION_TYPE;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.domain.seminar.dto.AdminCreateSeminarRequestDto;
import pro.hexa.backend.main.api.domain.seminar.dto.AdminModifySeminarRequestDto;
import pro.hexa.backend.main.api.domain.seminar.dto.AdminSeminarDetailResponse;
import pro.hexa.backend.main.api.domain.seminar.dto.AdminSeminarListResponse;
import pro.hexa.backend.main.api.domain.seminar.dto.SeminarListResponse;

@SpringBootTest
class SeminarAdminPageControllerTest {

    @Autowired
    private SeminarAdminPageController seminarAdminPageController;
    @Autowired
    private SeminarRepository seminarRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
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

        List<Seminar> seminars = new ArrayList<>();
        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2024, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 2, 1, 1),
                user1,
                new ArrayList<>()
        ));
        seminarRepository.saveAll(seminars);

        // when
        AdminSeminarListResponse response = seminarAdminPageController.getAdminSeminarList(1,3).getBody();

        // then
        assertThat(response).isNotNull();

        assertEquals(response.getTotalPage(), 0);
        assertEquals(response.getList().size(), 0);
    }

    @Test
    void getAdminSeminarDetail() {
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

        List<Seminar> seminars = new ArrayList<>();
        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2024, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 2, 1, 1),
                user1,
                new ArrayList<>()
        ));
        seminarRepository.saveAll(seminars);

        AdminSeminarDetailResponse response = seminarAdminPageController.getAdminSeminarDetail(7L).getBody();

        assertThat(response).isNotNull();
    }

    @Test
    void adminCreateSeminar() {
        List<Long> longList = new ArrayList<>();
        longList.add(100L);
        longList.add(200L);
        longList.add(300L);
        Date currentdate = new Date();
        AdminCreateSeminarRequestDto adminCreateSeminarRequestDto = new AdminCreateSeminarRequestDto("title1", "this is content1", currentdate, longList);

        seminarAdminPageController.adminCreateSeminar(adminCreateSeminarRequestDto);

        //id 값 지정못하기 때문에 무조건 attactment못찾았다고 exception 뜰텐데
        Attachment attachment1 = Attachment.create("1","att1", 100L);
        attachmentRepository.save(attachment1);
        List<Seminar> seminars = seminarRepository.findAll();
        assertEquals(seminars.size(), 0);
    }

    @Test
    void adminModifySeminar() {
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
        List<Long> longList = new ArrayList<>();
        longList.add(100L);
        longList.add(200L);
        longList.add(300L);
        AdminModifySeminarRequestDto adminCreateSeminarRequestDto = new AdminModifySeminarRequestDto(100L, "title1", "This is content1", LocalDateTime.of(2023, 1, 11,1,1),longList);

        List<Seminar> seminars = new ArrayList<>();
        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2024, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 2, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminarRepository.saveAll(seminars);

        seminarAdminPageController.adminModifySeminar(adminCreateSeminarRequestDto);

        List<Seminar> seminarv = seminarRepository.findAll();
        assertEquals(seminarv.size(), 0);
    }

    @Test
    void adminDeleteSeminar() {

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

        List<Seminar> seminars = new ArrayList<>();
        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2024, 1, 1, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminars.add(Seminar.create(
                LocalDateTime.of(2023, 1, 2, 1, 1),
                user1,
                new ArrayList<>()
        ));

        seminarRepository.saveAll(seminars);

        seminarAdminPageController.adminDeleteSeminar(100L);

    }
}