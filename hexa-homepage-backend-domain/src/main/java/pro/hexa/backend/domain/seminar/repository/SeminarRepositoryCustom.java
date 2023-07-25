package pro.hexa.backend.domain.seminar.repository;

import pro.hexa.backend.domain.seminar.domain.Seminar;
import java.util.List;

public interface SeminarRepositoryCustom {

    List<Seminar> findAllByQuery(String searchText, Integer year, Integer pageNum, Integer page);

    int getMaxPage(String searchText, Integer year, Integer pageNum, Integer page);
}
