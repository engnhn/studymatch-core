package tr.com.enginhanzengin.studymatchcore.application.service;

import tr.com.enginhanzengin.studymatchcore.application.dto.*;
import tr.com.enginhanzengin.studymatchcore.domain.Student;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    AdminLoginResponse login(AdminLoginRequest request);

    boolean validateToken(String token);

    List<Student> getAllStudents();

    Student getStudentById(UUID id);

    CourseDTO createCourse(CourseRequest request);

    CourseDTO updateCourse(UUID id, CourseRequest request);

    void deleteCourse(UUID id);

    List<StudyGroupDTO> getAllGroups();

    void deleteGroup(UUID id);
}
