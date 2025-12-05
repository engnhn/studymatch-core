package tr.com.enginhanzengin.studymatchcore.engine.strategies;

import org.springframework.stereotype.Component;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.engine.ScoringStrategy;

@Component
public class TimeIntersectionStrategy implements ScoringStrategy {

    @Override
    public double calculateScore(Student student1, Student student2) {
        if (student1.getSchedule() == null || student2.getSchedule() == null)
            return 0.0;

        String days1 = student1.getSchedule().getAvailableDays();
        String days2 = student2.getSchedule().getAvailableDays();

        if (days1 == null || days2 == null)
            return 0.0;

        if (days1.equals(days2))
            return 1.0;

        return 0.5;
    }

    @Override
    public double getWeight() {
        return 0.25;
    }
}
