package tr.com.enginhanzengin.studymatchcore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tr.com.enginhanzengin.studymatchcore.domain.vo.Schedule;
import tr.com.enginhanzengin.studymatchcore.domain.vo.TopicPreference;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Course> courses = new java.util.ArrayList<>();

    @Embedded
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ElementCollection
    private List<TopicPreference> topicPreferences;

    @OneToMany(cascade = CascadeType.ALL)
    private List<StudyGoal> studyGoals;

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
