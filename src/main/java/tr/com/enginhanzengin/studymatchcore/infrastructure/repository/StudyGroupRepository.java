package tr.com.enginhanzengin.studymatchcore.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.enginhanzengin.studymatchcore.domain.StudyGroup;

import java.util.UUID;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, UUID> {
}
