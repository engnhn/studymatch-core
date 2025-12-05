package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.enginhanzengin.studymatchcore.application.dto.SchoolDTO;
import tr.com.enginhanzengin.studymatchcore.domain.School;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.SchoolRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchoolServiceImplTest {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolServiceImpl schoolService;

    @Test
    void createSchool_ShouldReturnSchoolDTO() {
        School school = new School(UUID.randomUUID(), "ITU");
        when(schoolRepository.save(any(School.class))).thenReturn(school);

        SchoolDTO result = schoolService.createSchool("ITU");

        assertNotNull(result);
        assertEquals("ITU", result.getName());
    }

    @Test
    void getAllSchools_ShouldReturnListOfSchoolDTOs() {
        School s1 = new School(UUID.randomUUID(), "ITU");
        School s2 = new School(UUID.randomUUID(), "ODTU");
        when(schoolRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<SchoolDTO> result = schoolService.getAllSchools();

        assertEquals(2, result.size());
        assertEquals("ITU", result.get(0).getName());
        assertEquals("ODTU", result.get(1).getName());
    }
}
