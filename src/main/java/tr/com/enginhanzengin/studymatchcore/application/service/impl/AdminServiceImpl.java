package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.enginhanzengin.studymatchcore.application.dto.*;
import tr.com.enginhanzengin.studymatchcore.application.mapper.CourseMapper;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudyGroupMapper;
import tr.com.enginhanzengin.studymatchcore.application.service.AdminService;
import tr.com.enginhanzengin.studymatchcore.domain.Admin;
import tr.com.enginhanzengin.studymatchcore.domain.Course;
import tr.com.enginhanzengin.studymatchcore.domain.StudyGroup;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.AdminRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.CourseRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudyGroupRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final tr.com.enginhanzengin.studymatchcore.infrastructure.repository.SchoolRepository schoolRepository;
    private final CourseMapper courseMapper;
    private final StudyGroupMapper studyGroupMapper;

     // Map<Token, AdminId>
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @org.springframework.beans.factory.annotation.Value("${admin.token}")
    private String staticAdminToken;

    // Map<Token, AdminId>
    private final java.util.Map<String, UUID> activeTokens = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = UUID.randomUUID().toString();
        activeTokens.put(token, admin.getId());
        return new AdminLoginResponse(token);
    }

    @Override
    public boolean validateToken(String token) {
        return activeTokens.containsKey(token) || (staticAdminToken != null && staticAdminToken.equals(token));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    @Transactional
    public CourseDTO createCourse(CourseRequest request) {
        if (courseRepository.findByCode(request.getCode()).isPresent()) {
            throw new RuntimeException("Course with this code already exists");
        }
        Course course = new Course();
        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCredit(request.getCredit());

        Course saved = courseRepository.save(course);
        return courseMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public CourseDTO updateCourse(UUID id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCredit(request.getCredit());

        Course updated = courseRepository.save(course);
        return courseMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void deleteCourse(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    @Override
    public List<StudyGroupDTO> getAllGroups() {
        return studyGroupRepository.findAll().stream()
                .map(studyGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGroup(UUID id) {
        if (!studyGroupRepository.existsById(id)) {
            throw new RuntimeException("Group not found");
        }
        studyGroupRepository.deleteById(id);
    }
}
