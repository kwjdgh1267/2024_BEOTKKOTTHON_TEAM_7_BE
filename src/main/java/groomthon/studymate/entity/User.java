package groomthon.studymate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;



    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();


}
