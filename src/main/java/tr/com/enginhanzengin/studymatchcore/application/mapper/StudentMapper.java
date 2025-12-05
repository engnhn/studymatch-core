package tr.com.enginhanzengin.studymatchcore.application.mapper;

import org.springframework.stereotype.Component;
import tr.com.enginhanzengin.studymatchcore.application.dto.StudentDTO;
import tr.com.enginhanzengin.studymatchcore.domain.Student;

@Component
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setSchedule(student.getSchedule());
        dto.setTopicPreferences(student.getTopicPreferences());
        return dto;
    }
}
