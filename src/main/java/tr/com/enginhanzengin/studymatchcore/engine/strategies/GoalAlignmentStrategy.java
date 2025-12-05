package tr.com.enginhanzengin.studymatchcore.engine.strategies;

import org.springframework.stereotype.Component;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.engine.ScoringStrategy;

@Component
public class GoalAlignmentStrategy implements ScoringStrategy {

    @Override
    public double calculateScore(Student student1, Student student2) {
        return 0.8;
    }

    @Override
    public double getWeight() {
        return 0.20;
    }
}
