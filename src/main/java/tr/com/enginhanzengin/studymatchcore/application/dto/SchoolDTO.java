package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {
    private UUID id;
    private String name;
}
