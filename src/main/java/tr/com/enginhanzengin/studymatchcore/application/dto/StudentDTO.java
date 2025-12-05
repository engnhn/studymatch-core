package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;
import tr.com.enginhanzengin.studymatchcore.domain.vo.Schedule;
import tr.com.enginhanzengin.studymatchcore.domain.vo.TopicPreference;

import java.util.List;
import java.util.UUID;

@Data
public class StudentDTO {
    private UUID id;
    private String name;
    private String email;
    private Schedule schedule;
    private List<TopicPreference> topicPreferences;
}
