package pro.hexa.backend.domain.news.repository;

import java.util.List;
import java.util.Optional;

import pro.hexa.backend.domain.news.domain.News;

public interface NewsRepositoryCustom {

    List<News> findForMainPageByQuery();
    Optional<News> findByTitle(String title);
}
