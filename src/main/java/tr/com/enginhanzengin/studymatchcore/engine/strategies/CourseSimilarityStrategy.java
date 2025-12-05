package tr.com.enginhanzengin.studymatchcore.engine.strategies;

import org.springframework.stereotype.Component;
import tr.com.enginhanzengin.studymatchcore.domain.Course;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.engine.ScoringStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseSimilarityStrategy implements ScoringStrategy {

    @Override
    public double calculateScore(Student student1, Student student2) {
        if (student1.getCourses() == null || student2.getCourses() == null)
            return 0.0;

        Set<String> courses1 = student1.getCourses().stream().map(Course::getCode).collect(Collectors.toSet());
        Set<String> courses2 = student2.getCourses().stream().map(Course::getCode).collect(Collectors.toSet());

        Set<String> intersection = new HashSet<>(courses1);
        intersection.retainAll(courses2);

        Set<String> union = new HashSet<>(courses1);
        union.addAll(courses2);

        if (union.isEmpty())
            return 0.0;

        return (double) intersection.size() / union.size();
    }

    @Override
    public double getWeight() {
        return 0.30;
    }
}
