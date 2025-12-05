package tr.com.enginhanzengin.studymatchcore.infrastructure.seeder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import tr.com.enginhanzengin.studymatchcore.domain.Admin;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.AdminRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminSeederTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminSeeder adminSeeder;

    @Test
    void run_ShouldCreateAdmin_WhenAdminDoesNotExist() throws Exception {
        // Arrange
        ReflectionTestUtils.setField(adminSeeder, "adminEmail", "admin@test.com");
        ReflectionTestUtils.setField(adminSeeder, "adminPassword", "password");
        ReflectionTestUtils.setField(adminSeeder, "adminToken", "token");

        when(adminRepository.findByEmail("admin@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        adminSeeder.run();

        // Assert
        verify(adminRepository).save(any(Admin.class));
    }

    @Test
    void run_ShouldNotCreateAdmin_WhenAdminAlreadyExists() throws Exception {
        // Arrange
        ReflectionTestUtils.setField(adminSeeder, "adminEmail", "admin@test.com");

        when(adminRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(new Admin()));

        // Act
        adminSeeder.run();

        // Assert
        verify(adminRepository, never()).save(any(Admin.class));
    }
}
