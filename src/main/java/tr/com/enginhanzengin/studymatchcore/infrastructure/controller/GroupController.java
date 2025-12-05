package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tr.com.enginhanzengin.studymatchcore.application.dto.CreateGroupRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudyGroupDTO;
import tr.com.enginhanzengin.studymatchcore.application.service.GroupService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.UUID;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Tag(name = "Group", description = "Study Group management APIs")
public class GroupController {

    private final GroupService groupService;
    private final StudentRepository studentRepository;

    @Operation(summary = "Create a new study group", description = "Creates a new study group with the given name.")
    @PostMapping
    public ResponseEntity<StudyGroupDTO> createGroup(@RequestBody CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @Operation(summary = "Join a study group", description = "Adds the current student to the specified study group.")
    @PostMapping("/{id}/join")
    public ResponseEntity<StudyGroupDTO> joinGroup(@PathVariable UUID id, Authentication authentication) {
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(groupService.addStudentToGroup(id, student.getId()));
    }
}
