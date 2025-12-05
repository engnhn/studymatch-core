package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.CreateStudentRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.RegisterRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.UpdateStudentRequest;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudentMapper;
import tr.com.enginhanzengin.studymatchcore.application.service.StudentService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.CourseRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.SchoolRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentMapper studentMapper; // Keep this for other methods

    @Override
    @Transactional
    public Student register(RegisterRequest request) {
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));

        UUID schoolId = request.getSchoolId();
        if (schoolId != null) {
            tr.com.enginhanzengin.studymatchcore.domain.School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new RuntimeException("School not found"));
            student.setSchool(school);
        } else {
            throw new RuntimeException("School is required");
        }

        return studentRepository.save(student);
    }

    @Override
    public StudentDTO updateStudent(UUID id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (request.getName() != null) {
            student.setName(request.getName());
        }
        if (request.getSchedule() != null) {
            student.setSchedule(request.getSchedule());
        }
        if (request.getTopicPreferences() != null) {
            student.setTopicPreferences(request.getTopicPreferences());
        }

        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toDTO(updatedStudent);
    }

    @Override
    public StudentDTO addCourse(UUID id, CourseDTO courseDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        tr.com.enginhanzengin.studymatchcore.domain.Course course = new tr.com.enginhanzengin.studymatchcore.domain.Course();
        course.setCode(courseDTO.getCode());
        course.setName(courseDTO.getName());

        student.addCourse(course);

        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toDTO(updatedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudent(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return studentMapper.toDTO(student);
    }
}
