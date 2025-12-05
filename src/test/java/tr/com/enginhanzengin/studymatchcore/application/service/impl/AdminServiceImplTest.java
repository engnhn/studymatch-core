package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tr.com.enginhanzengin.studymatchcore.application.dto.AdminLoginRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.AdminLoginResponse;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseRequest;
import tr.com.enginhanzengin.studymatchcore.application.mapper.CourseMapper;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudyGroupMapper;
import tr.com.enginhanzengin.studymatchcore.domain.Admin;
import tr.com.enginhanzengin.studymatchcore.domain.Course;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.AdminRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.CourseRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudyGroupRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudyGroupRepository studyGroupRepository;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private StudyGroupMapper studyGroupMapper;

    @Mock
    private tr.com.enginhanzengin.studymatchcore.infrastructure.repository.SchoolRepository schoolRepository;
    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ... existing tests ...

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        Admin admin = new Admin(UUID.randomUUID(), "admin@test.com", "encodedPassword", "SUPER_ADMIN");
        when(adminRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        AdminLoginRequest request = new AdminLoginRequest();
        request.setEmail("admin@test.com");
        request.setPassword("password");

        AdminLoginResponse response = adminService.login(request);

        assertNotNull(response.getToken());
        assertTrue(adminService.validateToken(response.getToken()));
    }

    @Test
    void login_ShouldThrowException_WhenCredentialsAreInvalid() {
        when(adminRepository.findByEmail("admin@test.com")).thenReturn(Optional.empty());

        AdminLoginRequest request = new AdminLoginRequest();
        request.setEmail("admin@test.com");
        request.setPassword("password");

        assertThrows(RuntimeException.class, () -> adminService.login(request));
    }

    @Test
    void createCourse_ShouldCreateCourse_WhenCodeIsUnique() {
        CourseRequest request = new CourseRequest();
        request.setCode("CS101");
        request.setName("Intro to CS");

        when(courseRepository.findByCode("CS101")).thenReturn(Optional.empty());
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);
        when(courseMapper.toDTO(any(Course.class)))
                .thenReturn(new CourseDTO(UUID.randomUUID(), "CS101", "Intro to CS", null, null));

        CourseDTO result = adminService.createCourse(request);

        assertNotNull(result);
        assertEquals("CS101", result.getCode());
        verify(courseRepository).save(any(Course.class));
    }
}
