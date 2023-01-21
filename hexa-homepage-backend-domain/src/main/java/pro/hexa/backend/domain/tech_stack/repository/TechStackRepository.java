package pro.hexa.backend.domain.tech_stack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.tech_stack.domain.TechStack;

@Repository
public interface TechStackRepository extends JpaRepository<TechStack, Long>, TechStackRepositoryCustom {

}
