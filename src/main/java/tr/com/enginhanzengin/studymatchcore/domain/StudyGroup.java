package tr.com.enginhanzengin.studymatchcore.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private Integer capacity;
    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Student creator;

    @ElementCollection
    @CollectionTable(name = "study_group_tags", joinColumns = @JoinColumn(name = "study_group_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    private java.time.LocalDateTime scheduledStartTime;

    @ManyToMany
    @JoinTable(name = "study_group_members", joinColumns = @JoinColumn(name = "study_group_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> members = new ArrayList<>();

    public void addMember(Student student) {
        this.members.add(student);
    }
}
