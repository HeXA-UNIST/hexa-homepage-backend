package pro.hexa.backend.domain.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.hexa.backend.domain.news.domain.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, NewsRepositoryCustom {

}
