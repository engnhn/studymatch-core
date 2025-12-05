package tr.com.enginhanzengin.studymatchcore.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.enginhanzengin.studymatchcore.domain.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByEmail(String email);
}
