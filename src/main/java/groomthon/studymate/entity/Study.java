package groomthon.studymate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Study {
    @Id
    @GeneratedValue
    @Column(name = "study_id")
    private Long id;

    private String title;


    @OneToMany(mappedBy = "study")
    private List<Comment> comments= new ArrayList<>();

    @OneToMany(mappedBy = "study")
    private List<Heart> hearts = new ArrayList<>();

    private String part;

    private long recruitNum;

    private boolean isCompleted;


}

