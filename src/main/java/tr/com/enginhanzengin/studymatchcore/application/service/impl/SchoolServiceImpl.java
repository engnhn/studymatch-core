package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.enginhanzengin.studymatchcore.application.dto.SchoolDTO;
import tr.com.enginhanzengin.studymatchcore.application.service.SchoolService;
import tr.com.enginhanzengin.studymatchcore.domain.School;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.SchoolRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    @Transactional
    public SchoolDTO createSchool(String name) {
        School school = new School();
        school.setName(name);
        School saved = schoolRepository.save(school);
        return new SchoolDTO(saved.getId(), saved.getName());
    }

    @Override
    public List<SchoolDTO> getAllSchools() {
        return schoolRepository.findAll().stream()
                .map(s -> new SchoolDTO(s.getId(), s.getName()))
                .collect(Collectors.toList());
    }
}
