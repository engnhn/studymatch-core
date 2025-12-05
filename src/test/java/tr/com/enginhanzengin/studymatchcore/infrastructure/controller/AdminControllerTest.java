package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tr.com.enginhanzengin.studymatchcore.application.dto.AdminLoginRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.AdminLoginResponse;
import tr.com.enginhanzengin.studymatchcore.application.service.AdminService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private tr.com.enginhanzengin.studymatchcore.application.service.SchoolService schoolService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_ShouldReturnToken() throws Exception {
        AdminLoginRequest request = new AdminLoginRequest();
        request.setEmail("admin@test.com");
        request.setPassword("password");

        when(adminService.login(any(AdminLoginRequest.class))).thenReturn(new AdminLoginResponse("test-token"));

        mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-token"));
    }

    @Test
    void getAllStudents_ShouldReturnOk_WhenTokenIsValid() throws Exception {
        when(adminService.validateToken("valid-token")).thenReturn(true);

        mockMvc.perform(get("/api/admin/students")
                .header("X-Admin-Token", "valid-token"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStudents_ShouldReturnUnauthorized_WhenTokenIsInvalid() throws Exception {
        when(adminService.validateToken("invalid-token")).thenReturn(false);

        mockMvc.perform(get("/api/admin/students")
                .header("X-Admin-Token", "invalid-token"))
                .andExpect(status().isUnauthorized());
    }
}
