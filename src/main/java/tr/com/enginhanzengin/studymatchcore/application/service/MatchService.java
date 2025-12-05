package tr.com.enginhanzengin.studymatchcore.application.service;

import tr.com.enginhanzengin.studymatchcore.application.dto.MatchResultDTO;

import java.util.List;
import java.util.UUID;

public interface MatchService {
    List<MatchResultDTO> findMatches(UUID studentId);
}
