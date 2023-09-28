package pro.hexa.backend.domain.news.repository;

import java.util.List;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.project.domain.Project;

public interface NewsRepositoryCustom {

    List<News> findForMainPageByQuery();

    List<News> findAllByQuery(Integer pageNum, Integer perPage);

    News findByQuery(Long newsId);

    int getMaxPage(Integer perPage);
}
