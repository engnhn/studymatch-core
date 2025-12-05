package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;

@Data
public class CreateStudentRequest {
    private String name;
    private String email;
    private String password;
}
