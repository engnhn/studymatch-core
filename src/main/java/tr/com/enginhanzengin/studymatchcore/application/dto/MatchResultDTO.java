package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResultDTO {
    private StudentDTO candidate;
    private double score;
}
