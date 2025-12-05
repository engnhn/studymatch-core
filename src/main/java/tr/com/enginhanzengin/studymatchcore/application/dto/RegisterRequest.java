package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private java.util.UUID schoolId;
}
