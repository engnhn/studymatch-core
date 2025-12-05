package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.application.dto.UpdateStudentRequest;
import tr.com.enginhanzengin.studymatchcore.application.service.StudentService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Student management APIs")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Operation(summary = "Get current student profile", description = "Retrieves the profile of the currently authenticated student.")
    @GetMapping("/me")
    public ResponseEntity<StudentDTO> getCurrentStudent(Authentication authentication) {
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(studentService.getStudent(student.getId()));
    }

    @Operation(summary = "Update current student profile", description = "Updates the profile of the currently authenticated student.")
    @PutMapping("/me")
    public ResponseEntity<StudentDTO> updateCurrentStudent(@RequestBody UpdateStudentRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(studentService.updateStudent(student.getId(), request));
    }

    @Operation(summary = "Add course to student", description = "Adds a new course to the currently authenticated student's profile.")
    @PostMapping("/me/courses")
    public ResponseEntity<StudentDTO> addCourse(
            @RequestBody tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO courseDTO,
            Authentication authentication) {
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(studentService.addCourse(student.getId(), courseDTO));
    }
}
