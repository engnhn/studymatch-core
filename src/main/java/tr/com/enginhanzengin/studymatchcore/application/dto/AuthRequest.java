package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
