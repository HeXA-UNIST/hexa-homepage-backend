package pro.hexa.backend.domain.project_member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.project_member.domain.ProjectMember;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long>, ProjectMemberRepositoryCustom {
}
