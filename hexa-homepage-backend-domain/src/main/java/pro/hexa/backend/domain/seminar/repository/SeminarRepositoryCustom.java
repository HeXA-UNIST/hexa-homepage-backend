package pro.hexa.backend.domain.seminar.repository;

import java.util.List;
import pro.hexa.backend.domain.seminar.domain.Seminar;

public interface SeminarRepositoryCustom {

    List<Seminar> findAllByQuery(String searchText, Integer year, Integer pageNum, Integer perPage);

    int getMaxPage(String searchText, Integer year, Integer perPage);
}
