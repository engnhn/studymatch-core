package tr.com.enginhanzengin.studymatchcore.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.enginhanzengin.studymatchcore.domain.School;

import java.util.UUID;

public interface SchoolRepository extends JpaRepository<School, UUID> {
}
