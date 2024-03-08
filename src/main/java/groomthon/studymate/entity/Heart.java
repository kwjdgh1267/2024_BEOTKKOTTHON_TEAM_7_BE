package groomthon.studymate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Heart {
    @Id
    @GeneratedValue
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
