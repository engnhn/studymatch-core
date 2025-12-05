package tr.com.enginhanzengin.studymatchcore.application.service;

import tr.com.enginhanzengin.studymatchcore.application.dto.SchoolDTO;
import java.util.List;

public interface SchoolService {
    SchoolDTO createSchool(String name);

    List<SchoolDTO> getAllSchools();
}
