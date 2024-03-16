package groomthon.studymate.entity;

import groomthon.studymate.entity.tag.Frequency;
import groomthon.studymate.entity.tag.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Study extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "study_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;


    @OneToMany(mappedBy = "study")
    private List<Comment> comments= new ArrayList<>();

    @OneToMany(mappedBy = "study")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "study")
    private List<UserStudy> userStudies = new ArrayList<>();

    @Column
    @Enumerated(value=EnumType.STRING)
    private Subject subject;

    @Column
    private long recruitNum;

    @Column
    private boolean complete;

    @Column
    @Enumerated(value=EnumType.STRING)
    private Frequency frequency;

    @Column
    private String writer;

    public Study(String title, String contents, Subject subject, long recruitNum, boolean isCompleted, Frequency frequency,String email) {
        this.title = title;
        this.contents = contents;
        this.subject = subject;
        this.recruitNum = recruitNum;
        this.complete = isCompleted;
        this.frequency = frequency;
        this.writer=email;
    }



}

