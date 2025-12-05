package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String email;
    private String password;
}
