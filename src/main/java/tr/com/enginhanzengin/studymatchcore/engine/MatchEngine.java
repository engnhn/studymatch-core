package tr.com.enginhanzengin.studymatchcore.engine;

import org.springframework.stereotype.Service;
import tr.com.enginhanzengin.studymatchcore.domain.Student;

import java.util.List;

@Service
public class MatchEngine {

    private final List<ScoringStrategy> strategies;

    public MatchEngine(List<ScoringStrategy> strategies) {
        this.strategies = strategies;
    }

    public double calculateMatchScore(Student s1, Student s2) {
        double totalScore = 0.0;
        double totalWeight = 0.0;

        for (ScoringStrategy strategy : strategies) {
            totalScore += strategy.calculateScore(s1, s2) * strategy.getWeight();
            totalWeight += strategy.getWeight();
        }

        return totalWeight > 0 ? totalScore / totalWeight : 0.0;
    }
}
