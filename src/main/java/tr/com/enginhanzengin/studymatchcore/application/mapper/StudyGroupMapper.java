package tr.com.enginhanzengin.studymatchcore.application.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudyGroupDTO;
import tr.com.enginhanzengin.studymatchcore.domain.StudyGroup;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudyGroupMapper {

    private final StudentMapper studentMapper;

    public StudyGroupDTO toDTO(StudyGroup group) {
        if (group == null) {
            return null;
        }
        StudyGroupDTO dto = new StudyGroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setCapacity(group.getCapacity());
        dto.setPrivate(group.isPrivate());
        dto.setScheduledStartTime(group.getScheduledStartTime());
        dto.setTags(group.getTags());

        if (group.getCourse() != null) {
            dto.setCourseId(group.getCourse().getId());
        }
        if (group.getCreator() != null) {
            dto.setCreatorId(group.getCreator().getId());
        }

        if (group.getMembers() != null) {
            dto.setMembers(group.getMembers().stream()
                    .map(studentMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
