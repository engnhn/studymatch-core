package tr.com.enginhanzengin.studymatchcore.application.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateGroupRequest {
    private String name;
    private String description;
    private Integer capacity;
    private boolean isPrivate;
    private UUID courseId;
    private UUID creatorId;
    private List<String> tags;
    private java.time.LocalDateTime scheduledStartTime;
}
