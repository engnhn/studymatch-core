package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;

@Data
public class CourseRequest {
    private String code;
    private String name;
    private String description;
    private Integer credit;
}
