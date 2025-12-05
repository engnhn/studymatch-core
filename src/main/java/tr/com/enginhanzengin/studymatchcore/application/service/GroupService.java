package tr.com.enginhanzengin.studymatchcore.application.service;

import tr.com.enginhanzengin.studymatchcore.application.dto.CreateGroupRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudyGroupDTO;

import java.util.UUID;

public interface GroupService {
    StudyGroupDTO createGroup(CreateGroupRequest request);

    StudyGroupDTO addStudentToGroup(UUID groupId, UUID studentId);
}
