package pro.hexa.backend.domain.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.service.domain.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>, ServiceRepositoryCustom {
}
