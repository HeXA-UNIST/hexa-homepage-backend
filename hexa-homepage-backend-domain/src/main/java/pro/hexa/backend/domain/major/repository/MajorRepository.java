package pro.hexa.backend.domain.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.major.domain.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long>, MajorRepositoryCustom {

}
