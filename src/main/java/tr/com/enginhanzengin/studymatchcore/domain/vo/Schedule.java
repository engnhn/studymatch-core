package tr.com.enginhanzengin.studymatchcore.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private String availableDays;
    private LocalTime preferredStartTime;
    private LocalTime preferredEndTime;
}
