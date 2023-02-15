package pro.hexa.backend.main.api.domain.seminar.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.seminar.domain.Seminar;
import pro.hexa.backend.domain.seminar.repository.SeminarRepository;
import pro.hexa.backend.main.api.domain.seminar.dto.SeminarDto;
import pro.hexa.backend.main.api.domain.seminar.dto.SeminarListResponse;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeminarPageService {
    private final SeminarRepository seminarRepository;

    public SeminarListResponse getSeminarListResponse(String searchText, Integer year, Integer pageNum, Integer page) {
        List<Seminar> seminarList = seminarRepository.findForSeminarListByQuery(searchText, year, pageNum, page);
        List<SeminarDto> seminars = seminarList.stream()
                .map(seminar -> {
                    SeminarDto seminarDto = new SeminarDto();
                    seminarDto.fromSeminar(seminar);
                    return seminarDto;
                })
                .collect(Collectors.toList());
        return SeminarListResponse.builder()
                .seminars(seminars)
                .page(page)
                .maxPage(3) // 임시로 넣어놨습니다
                .build();
    }
}