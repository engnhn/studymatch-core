package tr.com.enginhanzengin.studymatchcore.application.mapper;

import org.mapstruct.Mapper;
import tr.com.enginhanzengin.studymatchcore.application.dto.CourseDTO;
import tr.com.enginhanzengin.studymatchcore.domain.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO toDTO(Course course);

    Course toEntity(CourseDTO courseDTO);
}
