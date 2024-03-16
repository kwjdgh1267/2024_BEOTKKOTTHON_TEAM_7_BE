package groomthon.studymate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserStudy {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserStudy(Study study, User user) {
        this.study = study;
        this.user = user;
    }
    //연관관계 편의 메서드
    public void setStudy(Study study){
        this.study=study;
        study.getUserStudies().add(this);
    }

    public void setUser(User user){
        this.user=user;
        user.getUserStudies().add(this);
    }

}
