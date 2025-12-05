package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.enginhanzengin.studymatchcore.application.dto.CreateGroupRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudyGroupDTO;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudyGroupMapper;
import tr.com.enginhanzengin.studymatchcore.application.service.GroupService;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.domain.StudyGroup;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudyGroupRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudentRepository studentRepository;
    private final StudyGroupMapper studyGroupMapper;
    private final tr.com.enginhanzengin.studymatchcore.infrastructure.repository.CourseRepository courseRepository;

    @Override
    public StudyGroupDTO createGroup(CreateGroupRequest request) {
        StudyGroup group = new StudyGroup();
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setCapacity(request.getCapacity() != null ? request.getCapacity() : 4);
        group.setPrivate(request.isPrivate());
        group.setScheduledStartTime(request.getScheduledStartTime());

        if (request.getTags() != null) {
            group.setTags(request.getTags());
        }

        if (request.getCourseId() != null) {
            tr.com.enginhanzengin.studymatchcore.domain.Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            group.setCourse(course);
        }

        if (request.getCreatorId() != null) {
            Student creator = studentRepository.findById(request.getCreatorId())
                    .orElseThrow(() -> new RuntimeException("Creator student not found"));
            group.setCreator(creator);
            group.addMember(creator); // Creator is automatically a member
        }

        StudyGroup savedGroup = studyGroupRepository.save(group);
        return studyGroupMapper.toDTO(savedGroup);
    }

    @Override
    public StudyGroupDTO addStudentToGroup(UUID groupId, UUID studentId) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (group.getMembers().size() >= (group.getCapacity() != null ? group.getCapacity() : 4)) {
            throw new RuntimeException("Group is full");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        group.addMember(student);
        StudyGroup updatedGroup = studyGroupRepository.save(group);
        return studyGroupMapper.toDTO(updatedGroup);
    }
}
