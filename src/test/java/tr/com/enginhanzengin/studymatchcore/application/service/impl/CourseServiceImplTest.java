package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseRequest;
import tr.com.enginhanzengin.studymatchcore.application.mapper.CourseMapper;
import tr.com.enginhanzengin.studymatchcore.domain.Course;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.CourseRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private CourseRequest courseRequest;
    private Course course;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        courseRequest = new CourseRequest();
        courseRequest.setCode("CS101");
        courseRequest.setName("Intro to CS");
        courseRequest.setDescription("Description");
        courseRequest.setCredit(3);

        course = new Course();
        course.setId(UUID.randomUUID());
        course.setCode("CS101");
        course.setName("Intro to CS");
        course.setDescription("Description");
        course.setCredit(3);

        courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setCode("CS101");
        courseDTO.setName("Intro to CS");
        courseDTO.setDescription("Description");
        courseDTO.setCredit(3);
    }

    @Test
    void addCourse_ShouldCreateCourse_WhenCodeIsUnique() {
        when(courseRepository.findByCode("CS101")).thenReturn(Optional.empty());
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO result = courseService.addCourse(courseRequest);

        assertNotNull(result);
        assertEquals("CS101", result.getCode());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void addCourse_ShouldThrowException_WhenCodeExists() {
        when(courseRepository.findByCode("CS101")).thenReturn(Optional.of(course));

        assertThrows(RuntimeException.class, () -> courseService.addCourse(courseRequest));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void addCourseToStudent_ShouldAddCourse_WhenStudentAndCourseExist() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        courseService.addCourseToStudent(studentId, courseId);

        assertTrue(student.getCourses().contains(course));
        verify(studentRepository).save(student);
    }
}
