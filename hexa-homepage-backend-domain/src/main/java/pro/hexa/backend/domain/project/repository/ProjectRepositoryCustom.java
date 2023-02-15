package pro.hexa.backend.domain.project.repository;

import java.util.List;
import pro.hexa.backend.domain.project.domain.Project;

public interface ProjectRepositoryCustom {
    List<Project> findForProjectListByQuery(String searchText, List<String> status, String sort, List<String> includeTechStack,
                                            List<String> excludeTechStack, Integer year, Integer pageNum, Integer page);
    Project findForProjectByQuery(Long projectId);

    List<String> findTechStackByQuery();
}
