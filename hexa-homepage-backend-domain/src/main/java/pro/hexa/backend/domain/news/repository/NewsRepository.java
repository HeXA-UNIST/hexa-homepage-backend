package pro.hexa.backend.domain.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.hexa.backend.domain.news.domain.News;

public interface NewsRepository extends JpaRepository<Long, News>, NewsRepositoryCustom {

}
