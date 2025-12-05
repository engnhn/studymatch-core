package tr.com.enginhanzengin.studymatchcore.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tr.com.enginhanzengin.studymatchcore.application.dto.SchoolDTO;
import tr.com.enginhanzengin.studymatchcore.application.service.SchoolService;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.context.annotation.Import;
import tr.com.enginhanzengin.studymatchcore.infrastructure.security.SecurityConfig;

@WebMvcTest(SchoolController.class)
@Import(SecurityConfig.class)
class SchoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolService schoolService;

    @Test
    void getAllSchools_ShouldReturnListOfSchools_AndBePublic() throws Exception {
        SchoolDTO s1 = new SchoolDTO(UUID.randomUUID(), "ITU");
        SchoolDTO s2 = new SchoolDTO(UUID.randomUUID(), "ODTU");
        when(schoolService.getAllSchools()).thenReturn(Arrays.asList(s1, s2));

        mockMvc.perform(get("/api/schools")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ITU"))
                .andExpect(jsonPath("$[1].name").value("ODTU"));
    }
}
