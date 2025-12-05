package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.enginhanzengin.studymatchcore.application.dto.*;
import tr.com.enginhanzengin.studymatchcore.application.service.AdminService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Administrative operations")
public class AdminController {

    private final tr.com.enginhanzengin.studymatchcore.application.service.AdminService adminService;
    private final tr.com.enginhanzengin.studymatchcore.application.service.SchoolService schoolService;

    @PostMapping("/login")
    @Operation(summary = "Admin login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(adminService.login(request));
    }

    @GetMapping("/students")
    @Operation(summary = "Get all students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestHeader("X-Admin-Token") String token) {
        validateToken(token);
        return ResponseEntity.ok(adminService.getAllStudents());
    }

    @GetMapping("/students/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<Student> getStudentById(@RequestHeader("X-Admin-Token") String token, @PathVariable UUID id) {
        validateToken(token);
        return ResponseEntity.ok(adminService.getStudentById(id));
    }

    @PostMapping("/courses")
    @Operation(summary = "Create a new course")
    public ResponseEntity<CourseDTO> createCourse(@RequestHeader("X-Admin-Token") String token,
            @RequestBody CourseRequest request) {
        validateToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createCourse(request));
    }

    @PutMapping("/courses/{id}")
    @Operation(summary = "Update a course")
    public ResponseEntity<CourseDTO> updateCourse(@RequestHeader("X-Admin-Token") String token, @PathVariable UUID id,
            @RequestBody CourseRequest request) {
        validateToken(token);
        return ResponseEntity.ok(adminService.updateCourse(id, request));
    }

    @DeleteMapping("/courses/{id}")
    @Operation(summary = "Delete a course")
    public ResponseEntity<Void> deleteCourse(@RequestHeader("X-Admin-Token") String token, @PathVariable UUID id) {
        validateToken(token);
        adminService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groups")
    @Operation(summary = "Get all study groups")
    public ResponseEntity<List<StudyGroupDTO>> getAllGroups(@RequestHeader("X-Admin-Token") String token) {
        validateToken(token);
        return ResponseEntity.ok(adminService.getAllGroups());
    }

    @DeleteMapping("/groups/{id}")
    @Operation(summary = "Delete a study group")
    public ResponseEntity<Void> deleteGroup(@RequestHeader("X-Admin-Token") String token, @PathVariable UUID id) {
        validateToken(token);
        adminService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/schools")
    @Operation(summary = "Create a new school")
    public ResponseEntity<SchoolDTO> createSchool(@RequestHeader("X-Admin-Token") String token,
            @RequestBody SchoolDTO request) {
        validateToken(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolService.createSchool(request.getName()));
    }

    @GetMapping("/schools")
    @Operation(summary = "Get all schools")
    public ResponseEntity<List<SchoolDTO>> getAllSchools(@RequestHeader("X-Admin-Token") String token) {
        validateToken(token);
        return ResponseEntity.ok(schoolService.getAllSchools());
    }

    private void validateToken(String token) {
        if (!adminService.validateToken(token)) {
            throw new RuntimeException("Unauthorized: Invalid Admin Token"); // In real app, use a custom exception
                                                                             // mapped to 401
        }
    }

    // Exception handler for this controller to return 401/403/404 properly
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("Unauthorized")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        if (ex.getMessage().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
