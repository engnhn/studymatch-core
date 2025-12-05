package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tr.com.enginhanzengin.studymatchcore.application.dto.MatchResultDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.application.service.MatchService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.controller.MatchController;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.context.annotation.Import;
import tr.com.enginhanzengin.studymatchcore.infrastructure.security.SecurityConfig;

@WebMvcTest(MatchController.class)
@Import(SecurityConfig.class)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    @WithMockUser(username = "test@example.com")
    void getRecommendations_ShouldReturnMatches() throws Exception {
        UUID studentId = UUID.randomUUID();
        Student student = new Student();
        student.setId(studentId);
        student.setEmail("test@example.com");

        StudentDTO candidateDTO = new StudentDTO();
        candidateDTO.setName("Candidate");

        MatchResultDTO matchResult = new MatchResultDTO(candidateDTO, 0.85);

        when(studentRepository.findByEmail("test@example.com")).thenReturn(Optional.of(student));
        when(matchService.findMatches(studentId)).thenReturn(Collections.singletonList(matchResult));

        mockMvc.perform(get("/matches/recommendations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].score").value(0.85))
                .andExpect(jsonPath("$[0].candidate.name").value("Candidate"));
    }
}
