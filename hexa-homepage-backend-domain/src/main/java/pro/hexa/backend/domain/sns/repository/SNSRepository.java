package pro.hexa.backend.domain.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.sns.domain.SNS;

@Repository
public interface SNSRepository extends JpaRepository<SNS, Long>, SNSRepositoryCustom {

}
