package tr.com.enginhanzengin.studymatchcore.application.service;

import tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseRequest;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseDTO addCourse(CourseRequest request);

    List<CourseDTO> getAllCourses();

    void addCourseToStudent(UUID studentId, UUID courseId);
}
