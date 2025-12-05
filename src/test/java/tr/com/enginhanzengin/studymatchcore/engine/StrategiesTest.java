package tr.com.enginhanzengin.studymatchcore.engine;

import org.junit.jupiter.api.Test;
import tr.com.enginhanzengin.studymatchcore.domain.Course;
import tr.com.enginhanzengin.studymatchcore.domain.Student;
import tr.com.enginhanzengin.studymatchcore.domain.vo.Schedule;
import tr.com.enginhanzengin.studymatchcore.engine.strategies.CourseSimilarityStrategy;
import tr.com.enginhanzengin.studymatchcore.engine.strategies.TimeIntersectionStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class StrategiesTest {

    private final CourseSimilarityStrategy courseStrategy = new CourseSimilarityStrategy();
    private final TimeIntersectionStrategy timeStrategy = new TimeIntersectionStrategy();

    @Test
    void courseSimilarity_ShouldReturnOne_WhenCoursesAreIdentical() {
        Student s1 = new Student();
        s1.setCourses(
                Arrays.asList(new Course(null, "MATH101", "Math", null, null),
                        new Course(null, "PHYS101", "Physics", null, null)));

        Student s2 = new Student();
        s2.setCourses(
                Arrays.asList(new Course(null, "MATH101", "Math", null, null),
                        new Course(null, "PHYS101", "Physics", null, null)));

        double score = courseStrategy.calculateScore(s1, s2);
        assertEquals(1.0, score, 0.001);
    }

    @Test
    void courseSimilarity_ShouldReturnZero_WhenNoCommonCourses() {
        Student s1 = new Student();
        s1.setCourses(Collections.singletonList(new Course(null, "MATH101", "Math", null, null)));

        Student s2 = new Student();
        s2.setCourses(Collections.singletonList(new Course(null, "HIST101", "History", null, null)));

        double score = courseStrategy.calculateScore(s1, s2);
        assertEquals(0.0, score, 0.001);
    }

    @Test
    void courseSimilarity_ShouldReturnJaccardIndex_WhenPartialMatch() {
        // Intersection: MATH101 (1)
        // Union: MATH101, PHYS101, CHEM101 (3)
        // Score: 1/3 = 0.333...
        Student s1 = new Student();
        s1.setCourses(
                Arrays.asList(new Course(null, "MATH101", "Math", null, null),
                        new Course(null, "PHYS101", "Physics", null, null)));

        Student s2 = new Student();
        s2.setCourses(Arrays.asList(new Course(null, "MATH101", "Math", null, null),
                new Course(null, "CHEM101", "Chemistry", null, null)));

        double score = courseStrategy.calculateScore(s1, s2);
        assertEquals(1.0 / 3.0, score, 0.001);
    }

    @Test
    void timeIntersection_ShouldReturnOne_WhenSchedulesAreIdentical() {
        Student s1 = new Student();
        s1.setSchedule(new Schedule("Mon,Wed,Fri", null, null));

        Student s2 = new Student();
        s2.setSchedule(new Schedule("Mon,Wed,Fri", null, null));

        double score = timeStrategy.calculateScore(s1, s2);
        assertEquals(1.0, score, 0.001);
    }

    @Test
    void timeIntersection_ShouldReturnPartial_WhenSchedulesDiffer() {
        Student s1 = new Student();
        s1.setSchedule(new Schedule("Mon,Wed,Fri", null, null));

        Student s2 = new Student();
        s2.setSchedule(new Schedule("Tue,Thu", null, null));

        double score = timeStrategy.calculateScore(s1, s2);
        assertEquals(0.5, score, 0.001); // Based on current simplified logic
    }
}
