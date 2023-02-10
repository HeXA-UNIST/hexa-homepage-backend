package pro.hexa.backend.domain.project.repository;

import java.util.List;
import pro.hexa.backend.domain.project.domain.Project;

public interface ProjectRepositoryCustom {
    List<Project> findForProjectListByQuery();
    Project findForProjectByQuery(Long projectId);
}
