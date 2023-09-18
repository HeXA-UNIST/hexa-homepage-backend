package pro.hexa.backend.domain.project.repository;

import java.util.List;
import pro.hexa.backend.domain.project.domain.Project;

public interface ProjectRepositoryCustom {

    List<Project> findAllByQuery(String searchText, List<String> status, String sort, List<String> includeTechStack,
        List<String> excludeTechStack, Integer year, Integer pageNum, Integer perPage);

    Project findByQuery(Long projectId);

    int getMaxPage(String searchText, List<String> status, String sort, List<String> includeTechStack,
        List<String> excludeTechStack, Integer year, Integer perPage);
}
