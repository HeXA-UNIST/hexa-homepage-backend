package pro.hexa.backend.domain.news.repository;

import pro.hexa.backend.domain.news.domain.News;

import java.util.List;
import java.util.Optional;

public interface NewsRepositoryCustom {

    List<News> findForMainPageByQuery();

    List<News> findAllWithPaging(Integer pageNum, Integer perPage);

    Optional<News> findNewsByQuery(Long newsId);

    int getMaxPage(Integer perPage);
}
