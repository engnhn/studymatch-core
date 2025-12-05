package tr.com.enginhanzengin.studymatchcore.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicPreference {

    private String topicName;
    private int interestLevel; // 1-10
}
