package tr.com.enginhanzengin.studymatchcore.infrastructure.seeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tr.com.enginhanzengin.studymatchcore.domain.Admin;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.AdminRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.token}")
    private String adminToken;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.findByEmail(adminEmail).isEmpty()) {
            log.info("Seeding initial admin user...");
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole("SUPER_ADMIN");

            adminRepository.save(admin);
            log.info("Admin user created: {}", adminEmail);
        } else {
            log.info("Admin user already exists.");
        }
    }
}
