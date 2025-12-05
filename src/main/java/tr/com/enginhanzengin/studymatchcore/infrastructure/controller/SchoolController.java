package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.enginhanzengin.studymatchcore.application.dto.SchoolDTO;
import tr.com.enginhanzengin.studymatchcore.application.service.SchoolService;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
@Tag(name = "School Controller", description = "Public school operations")
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    @Operation(summary = "Get all schools (Public)")
    public ResponseEntity<List<SchoolDTO>> getAllSchools() {
        return ResponseEntity.ok(schoolService.getAllSchools());
    }
}
