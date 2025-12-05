package tr.com.enginhanzengin.studymatchcore.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.enginhanzengin.studymatchcore.domain.Student;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchEngineTest {

    @Mock
    private ScoringStrategy strategy1;

    @Mock
    private ScoringStrategy strategy2;

    private MatchEngine matchEngine;

    @BeforeEach
    void setUp() {
        matchEngine = new MatchEngine(Arrays.asList(strategy1, strategy2));
    }

    @Test
    void calculateMatchScore_ShouldReturnWeightedAverage() {
        Student s1 = new Student();
        Student s2 = new Student();

        // Strategy 1: Score 1.0, Weight 0.5
        when(strategy1.calculateScore(s1, s2)).thenReturn(1.0);
        when(strategy1.getWeight()).thenReturn(0.5);

        // Strategy 2: Score 0.5, Weight 0.5
        when(strategy2.calculateScore(s1, s2)).thenReturn(0.5);
        when(strategy2.getWeight()).thenReturn(0.5);

        // Total Score: (1.0 * 0.5) + (0.5 * 0.5) = 0.5 + 0.25 = 0.75
        // Total Weight: 0.5 + 0.5 = 1.0
        // Result: 0.75 / 1.0 = 0.75

        double result = matchEngine.calculateMatchScore(s1, s2);
        assertEquals(0.75, result, 0.001);
    }

    @Test
    void calculateMatchScore_ShouldReturnZero_WhenTotalWeightIsZero() {
        Student s1 = new Student();
        Student s2 = new Student();

        matchEngine = new MatchEngine(Collections.emptyList());

        double result = matchEngine.calculateMatchScore(s1, s2);
        assertEquals(0.0, result, 0.001);
    }
}
