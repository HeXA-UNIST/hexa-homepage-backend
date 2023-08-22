package pro.hexa.backend.domain.seminar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import pro.hexa.backend.domain.seminar.domain.Seminar;
import pro.hexa.backend.domain.seminar.repository.SeminarRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@Import({SeminarRepositoryImpl.class, JPAQueryFactory.class})
class SeminarRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SeminarRepository seminarRepository;

    @Test
    void testFindAllByQuery() {
        // Create test data or use existing data
        Seminar seminar1 = new Seminar();
        seminar1.setDate(LocalDateTime.of(2023, 1, 1,1,1));
        entityManager.persist(seminar1);

        entityManager.flush();
        entityManager.clear();

        List<Seminar> seminars = seminarRepository.findAllByQuery("Seminar", null, 0, 10);

        assertEquals(1, seminars.size());
        assertEquals("Seminar 1", seminars.get(0).getTitle());
    }
}
