package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.enginhanzengin.studymatchcore.application.dto.MatchResultDTO;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudentMapper;
import tr.com.enginhanzengin.studymatchcore.application.service.MatchService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.engine.MatchEngine;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final StudentRepository studentRepository;
    private final MatchEngine matchEngine;
    private final StudentMapper studentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MatchResultDTO> findMatches(UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getSchool() == null) {
            return java.util.Collections.emptyList();
        }

        List<Student> candidates = studentRepository.findAll().stream()
                .filter(c -> !c.getId().equals(studentId))
                .filter(c -> c.getSchool() != null && c.getSchool().getId().equals(student.getSchool().getId()))
                .collect(Collectors.toList());

        return candidates.stream()
                .map(candidate -> {
                    double score = matchEngine.calculateMatchScore(student, candidate);
                    return new MatchResultDTO(studentMapper.toDTO(candidate), score);
                })
                .sorted(Comparator.comparingDouble(MatchResultDTO::getScore).reversed())
                .collect(Collectors.toList());
    }
}
