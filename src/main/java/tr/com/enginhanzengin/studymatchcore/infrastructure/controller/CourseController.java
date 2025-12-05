package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseRequest;
import tr.com.enginhanzengin.studymatchcore.application.service.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.addCourse(request));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Void> addCourseToStudent(@PathVariable UUID courseId, @PathVariable UUID studentId) {
        courseService.addCourseToStudent(studentId, courseId);
        return ResponseEntity.ok().build();
    }
}
