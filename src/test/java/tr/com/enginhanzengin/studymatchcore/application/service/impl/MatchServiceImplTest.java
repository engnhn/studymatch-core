package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.enginhanzengin.studymatchcore.application.dto.MatchResultDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudentMapper;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.engine.MatchEngine;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MatchEngine matchEngine;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private MatchServiceImpl matchService;

    @Test
    void findMatches_ShouldReturnCandidates_WhenSameSchool() {
        UUID studentId = UUID.randomUUID();
        UUID schoolId = UUID.randomUUID();
        tr.com.enginhanzengin.studymatchcore.domain.School school = new tr.com.enginhanzengin.studymatchcore.domain.School(
                schoolId, "ITU");

        Student student = new Student();
        student.setId(studentId);
        student.setSchool(school);

        Student candidate = new Student();
        candidate.setId(UUID.randomUUID());
        candidate.setSchool(school);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student, candidate));
        when(matchEngine.calculateMatchScore(student, candidate)).thenReturn(0.8);

        StudentDTO candidateDTO = new StudentDTO();
        candidateDTO.setId(candidate.getId());
        when(studentMapper.toDTO(candidate)).thenReturn(candidateDTO);

        List<MatchResultDTO> results = matchService.findMatches(studentId);

        assertEquals(1, results.size());
        assertEquals(candidate.getId(), results.get(0).getCandidate().getId());
    }

    @Test
    void findMatches_ShouldNotReturnCandidates_WhenDifferentSchool() {
        UUID studentId = UUID.randomUUID();
        tr.com.enginhanzengin.studymatchcore.domain.School school1 = new tr.com.enginhanzengin.studymatchcore.domain.School(
                UUID.randomUUID(), "ITU");
        tr.com.enginhanzengin.studymatchcore.domain.School school2 = new tr.com.enginhanzengin.studymatchcore.domain.School(
                UUID.randomUUID(), "ODTU");

        Student student = new Student();
        student.setId(studentId);
        student.setSchool(school1);

        Student candidate = new Student();
        candidate.setId(UUID.randomUUID());
        candidate.setSchool(school2);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student, candidate));

        List<MatchResultDTO> results = matchService.findMatches(studentId);

        assertTrue(results.isEmpty());
    }
}
