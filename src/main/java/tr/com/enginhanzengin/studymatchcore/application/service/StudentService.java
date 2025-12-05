package tr.com.enginhanzengin.studymatchcore.application.service;

import tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.RegisterRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.UpdateStudentRequest;
import tr.com.enginhanzengin.studymatchcore.domain.Student;

import java.util.UUID;

public interface StudentService {
    Student register(RegisterRequest request);

    StudentDTO updateStudent(UUID id, UpdateStudentRequest request);

    StudentDTO addCourse(UUID id, CourseDTO courseDTO);

    StudentDTO getStudent(UUID id);
}
