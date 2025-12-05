package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.UpdateStudentRequest;
import tr.com.enginhanzengin.studymatchcore.application.service.StudentService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.controller.StudentController;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.context.annotation.Import;
import tr.com.enginhanzengin.studymatchcore.infrastructure.security.SecurityConfig;

@WebMvcTest(StudentController.class)
@Import(SecurityConfig.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test@example.com")
    void getCurrentStudent_ShouldReturnStudentProfile() throws Exception {
        UUID studentId = UUID.randomUUID();
        Student student = new Student();
        student.setId(studentId);
        student.setEmail("test@example.com");

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentId);
        studentDTO.setName("Test Student");
        studentDTO.setEmail("test@example.com");

        when(studentRepository.findByEmail("test@example.com")).thenReturn(Optional.of(student));
        when(studentService.getStudent(studentId)).thenReturn(studentDTO);

        mockMvc.perform(get("/students/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Student"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void updateCurrentStudent_ShouldReturnUpdatedProfile() throws Exception {
        UUID studentId = UUID.randomUUID();
        Student student = new Student();
        student.setId(studentId);
        student.setEmail("test@example.com");

        UpdateStudentRequest request = new UpdateStudentRequest();
        request.setName("Updated Name");

        StudentDTO updatedDTO = new StudentDTO();
        updatedDTO.setId(studentId);
        updatedDTO.setName("Updated Name");

        when(studentRepository.findByEmail("test@example.com")).thenReturn(Optional.of(student));
        when(studentService.updateStudent(eq(studentId), any(UpdateStudentRequest.class))).thenReturn(updatedDTO);

        mockMvc.perform(put("/students/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }
}
