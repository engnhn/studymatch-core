package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;
import tr.com.enginhanzengin.studymatchcore.domain.vo.Schedule;
import tr.com.enginhanzengin.studymatchcore.domain.vo.TopicPreference;

import java.util.List;

@Data
public class UpdateStudentRequest {
    private String name;
    private Schedule schedule;
    private List<TopicPreference> topicPreferences;
}
