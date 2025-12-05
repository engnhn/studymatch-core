package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private java.util.UUID id;
    private String code;
    private String name;
    private String description;
    private Integer credit;
}
