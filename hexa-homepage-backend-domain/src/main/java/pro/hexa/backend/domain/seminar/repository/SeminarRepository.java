package pro.hexa.backend.domain.seminar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.seminar.domain.Seminar;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long>, SeminarRepositoryCustom {

}
