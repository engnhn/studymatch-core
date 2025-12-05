package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tr.com.enginhanzengin.studymatchcore.application.dto.CreateStudentRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.RegisterRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudentMapper;
import tr.com.enginhanzengin.studymatchcore.application.service.impl.StudentServiceImpl;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.SchoolRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void register_ShouldCreateStudent_WhenEmailIsUniqueAndSchoolExists() {
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");
        request.setSchoolId(UUID.randomUUID());

        when(studentRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(schoolRepository.findById(any(UUID.class))).thenReturn(
                Optional.of(new tr.com.enginhanzengin.studymatchcore.domain.School(request.getSchoolId(), "ITU")));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArguments()[0]);

        Student result = studentService.register(request);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertNotNull(result.getSchool());
    }

    @Test
    void getStudent_ShouldReturnStudentDTO_WhenFound() {
        UUID id = UUID.randomUUID();
        Student student = new Student();
        student.setId(id);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);

        // Assuming studentId is meant to be 'id' from the test method scope
        // Assuming 'candidate' and 'matchEngine' are intended to be mocked or defined
        // for this test,
        // but they are not in the original context.
        // For the purpose of faithfully applying the change, I'm adding them as they
        // appear in the snippet,
        // which might lead to compilation errors if not fully defined elsewhere.
        UUID studentId = id; // Added to make studentId compile
        Student candidate = new Student(); // Added to make candidate compile
        candidate.setId(UUID.randomUUID());
        // Mocking matchEngine would require adding it as a @Mock field.
        // For now, I'll comment out the line that uses it to avoid compilation errors,
        // as the instruction was to add a mock for studentMapper, not matchEngine.
        // If matchEngine is intended to be mocked, it should be added to the class
        // fields.

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        // when(studentRepository.findAll()).thenReturn(Arrays.asList(student,
        // candidate)); // This line seems out of place for a getStudent test
        // when(matchEngine.calculateMatchScore(student, candidate)).thenReturn(0.8); //
        // matchEngine is not mocked in this class

        // The original test logic for getStudent_ShouldReturnStudentDTO_WhenFound was:
        when(studentMapper.toDTO(student)).thenReturn(studentDTO);
        StudentDTO result = studentService.getStudent(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }
}
