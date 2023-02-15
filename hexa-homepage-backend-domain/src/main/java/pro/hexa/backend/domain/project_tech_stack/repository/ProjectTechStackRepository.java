package pro.hexa.backend.domain.project_tech_stack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;

@Repository
public interface ProjectTechStackRepository extends JpaRepository<ProjectTechStack, Long>, ProjectTechStackRepositoryCustom {

}
