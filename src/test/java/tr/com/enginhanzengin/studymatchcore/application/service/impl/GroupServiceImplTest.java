package tr.com.enginhanzengin.studymatchcore.application.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.enginhanzengin.studymatchcore.application.dto.CreateGroupRequest;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudyGroupDTO;
import tr.com.enginhanzengin.studymatchcore.application.mapper.StudyGroupMapper;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.domain.StudyGroup;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudentRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.StudyGroupRepository;
import tr.com.enginhanzengin.studymatchcore.infrastructure.repository.CourseRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private StudyGroupRepository studyGroupRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudyGroupMapper studyGroupMapper;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    void createGroup_ShouldReturnGroupDTO() {
        CreateGroupRequest request = new CreateGroupRequest();
        request.setName("Math Group");
        request.setCapacity(4);

        StudyGroup group = new StudyGroup();
        group.setId(UUID.randomUUID());
        group.setName("Math Group");
        group.setCapacity(4);

        StudyGroupDTO groupDTO = new StudyGroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName("Math Group");
        groupDTO.setCapacity(4);

        when(studyGroupRepository.save(any(StudyGroup.class))).thenReturn(group);
        when(studyGroupMapper.toDTO(any(StudyGroup.class))).thenReturn(groupDTO);

        StudyGroupDTO result = groupService.createGroup(request);

        assertNotNull(result);
        assertEquals("Math Group", result.getName());
        assertEquals(4, result.getCapacity());
    }

    @Test
    void addStudentToGroup_ShouldAddMember_WhenCapacityAllows() {
        UUID groupId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        StudyGroup group = new StudyGroup();
        group.setId(groupId);
        group.setCapacity(4);

        Student student = new Student();
        student.setId(studentId);

        when(studyGroupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studyGroupRepository.save(any(StudyGroup.class))).thenReturn(group);
        when(studyGroupMapper.toDTO(any(StudyGroup.class))).thenReturn(new StudyGroupDTO());

        StudyGroupDTO result = groupService.addStudentToGroup(groupId, studentId);

        assertNotNull(result);
    }

    @Test
    void addStudentToGroup_ShouldThrowException_WhenGroupIsFull() {
        UUID groupId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        StudyGroup group = new StudyGroup();
        group.setId(groupId);
        group.setCapacity(1);
        group.addMember(new Student()); // Fill the group

        when(studyGroupRepository.findById(groupId)).thenReturn(Optional.of(group));

        assertThrows(RuntimeException.class, () -> groupService.addStudentToGroup(groupId, studentId));
    }
}
