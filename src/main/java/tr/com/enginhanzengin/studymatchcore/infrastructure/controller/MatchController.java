package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.enginhanzengin.studymatchcore.application.dto.MatchResultDTO;
import tr.com.enginhanzengin.studymatchcore.application.service.MatchService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
@Tag(name = "Match", description = "Matching APIs")
public class MatchController {

    private final MatchService matchService;
    private final StudentRepository studentRepository;

    @Operation(summary = "Get match recommendations", description = "Returns a list of recommended study partners for the current student, sorted by match score.")
    @GetMapping("/recommendations")
    public ResponseEntity<List<MatchResultDTO>> getRecommendations(Authentication authentication) {
        String email = authentication.getName();
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(matchService.findMatches(student.getId()));
    }
}
