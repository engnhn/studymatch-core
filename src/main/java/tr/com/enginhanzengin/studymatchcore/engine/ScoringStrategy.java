package tr.com.enginhanzengin.studymatchcore.engine;

import tr.com.enginhanzengin.studymatchcore.domain.Student;

public interface ScoringStrategy {
    double calculateScore(Student student1, Student student2);

    double getWeight();
}
