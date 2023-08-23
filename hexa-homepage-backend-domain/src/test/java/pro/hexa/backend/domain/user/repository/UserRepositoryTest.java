package pro.hexa.backend.domain.user.repository;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("DB에 user 저장")
    void saveUser() {
        //given
        User user = User.create(
            "seonuk",
            "asd@example.com",
            GENDER_TYPE.여,
            STATE_TYPE.휴학,
            (short) 2020,
            "20202020",
            "김선욱",
            "mangp123"
        );

        //when
        User savedUser = userRepository.save(user);

        //then
        Assertions.assertEquals(userRepository.count(), 1);

        Assertions.assertEquals(savedUser.getId(), user.getId());
        Assertions.assertEquals(savedUser.getEmail(), user.getEmail());
        Assertions.assertEquals(savedUser.getGender(), user.getGender());
        Assertions.assertEquals(savedUser.getState(), user.getState());
        Assertions.assertEquals(savedUser.getRegYear(), user.getRegYear());
        Assertions.assertEquals(savedUser.getRegNum(), user.getRegNum());
        Assertions.assertEquals(savedUser.getName(), user.getName());
        Assertions.assertEquals(savedUser.getPassword(), user.getPassword());

    }

    @Test
    @DisplayName("DB에서 Id로 user조회")
    void findUserById() {
        //given
        User user1 = User.create(
            "seonuk",
            "asd@example.com",
            GENDER_TYPE.여,
            STATE_TYPE.휴학,
            (short) 2020,
            "20202020",
            "김선욱",
            "mangp123"
        );
        User user2 = User.create(
            "yechan",
            "qwe@example.com",
            GENDER_TYPE.남,
            STATE_TYPE.재학,
            (short) 2022,
            "20221111",
            "박예찬",
            "oioi"
        );

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        //when
        Optional<User> foundUser1 = userRepository.findById("seonuk");
        Optional<User> foundUser2 = userRepository.findById("yechan");
        Optional<User> foundUser3 = userRepository.findById("wilson");

        //then
        Assertions.assertEquals(userRepository.count(), 2);

        Assertions.assertTrue(foundUser1.isPresent());
        Assertions.assertTrue(foundUser2.isPresent());
        Assertions.assertTrue(foundUser3.isEmpty());

        Assertions.assertEquals(foundUser1.get().getId(), user1.getId());
        Assertions.assertEquals(foundUser1.get().getEmail(), user1.getEmail());
        Assertions.assertEquals(foundUser1.get().getGender(), user1.getGender());
        Assertions.assertEquals(foundUser1.get().getState(), user1.getState());
        Assertions.assertEquals(foundUser1.get().getRegYear(), user1.getRegYear());
        Assertions.assertEquals(foundUser1.get().getRegNum(), user1.getRegNum());
        Assertions.assertEquals(foundUser1.get().getName(), user1.getName());
        Assertions.assertEquals(foundUser1.get().getPassword(), user1.getPassword());

        Assertions.assertEquals(foundUser2.get().getId(), user2.getId());
        Assertions.assertEquals(foundUser2.get().getEmail(), user2.getEmail());
        Assertions.assertEquals(foundUser2.get().getGender(), user2.getGender());
        Assertions.assertEquals(foundUser2.get().getState(), user2.getState());
        Assertions.assertEquals(foundUser2.get().getRegYear(), user2.getRegYear());
        Assertions.assertEquals(foundUser2.get().getRegNum(), user2.getRegNum());
        Assertions.assertEquals(foundUser2.get().getName(), user2.getName());
        Assertions.assertEquals(foundUser2.get().getPassword(), user2.getPassword());

    }

    @Test
    @DisplayName("DB에서 user가 잘 조회되는지 확인")
    void findUserByNameAndEmail() {
        //given
        User user1 = User.create(
            "seonuk",
            "asd@example.com",
            GENDER_TYPE.여,
            STATE_TYPE.휴학,
            (short) 2020,
            "20202020",
            "김선욱",
            "mangp123"
        );
        User user2 = User.create(
            "yechan",
            "qwe@example.com",
            GENDER_TYPE.남,
            STATE_TYPE.재학,
            (short) 2022,
            "20221111",
            "박예찬",
            "oioi"
        );

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        //when
        Optional<User> foundUser1 = userRepository.findByNameAndEmail("seonuk","asd@example.com");
        Optional<User> foundUser2 = userRepository.findByNameAndEmail("yechan","qwe@example.com");
        Optional<User> foundUser3 = userRepository.findByNameAndEmail("yechan","notRight@example.com");
        Optional<User> foundUser4 = userRepository.findByNameAndEmail("no one","asd@example.com");


        //then
        Assertions.assertEquals(userRepository.count(), 2);

        Assertions.assertTrue(foundUser1.isPresent());
        Assertions.assertTrue(foundUser2.isPresent());
        Assertions.assertTrue(foundUser3.isEmpty());
        Assertions.assertTrue(foundUser4.isEmpty());

        Assertions.assertEquals(foundUser1.get().getId(), user1.getId());
        Assertions.assertEquals(foundUser1.get().getEmail(), user1.getEmail());
        Assertions.assertEquals(foundUser1.get().getGender(), user1.getGender());
        Assertions.assertEquals(foundUser1.get().getState(), user1.getState());
        Assertions.assertEquals(foundUser1.get().getRegYear(), user1.getRegYear());
        Assertions.assertEquals(foundUser1.get().getRegNum(), user1.getRegNum());
        Assertions.assertEquals(foundUser1.get().getName(), user1.getName());
        Assertions.assertEquals(foundUser1.get().getPassword(), user1.getPassword());

        Assertions.assertEquals(foundUser2.get().getId(), user2.getId());
        Assertions.assertEquals(foundUser2.get().getEmail(), user2.getEmail());
        Assertions.assertEquals(foundUser2.get().getGender(), user2.getGender());
        Assertions.assertEquals(foundUser2.get().getState(), user2.getState());
        Assertions.assertEquals(foundUser2.get().getRegYear(), user2.getRegYear());
        Assertions.assertEquals(foundUser2.get().getRegNum(), user2.getRegNum());
        Assertions.assertEquals(foundUser2.get().getName(), user2.getName());
        Assertions.assertEquals(foundUser2.get().getPassword(), user2.getPassword());

    }

}