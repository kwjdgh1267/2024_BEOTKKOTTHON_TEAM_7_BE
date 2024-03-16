package groomthon.studymate.dto;

import groomthon.studymate.entity.User;
import groomthon.studymate.entity.tag.Frequency;
import groomthon.studymate.entity.tag.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StudyResponseDto {
    private Long id;

    private String title;

    private String contents;

    private Subject subject;

    private long recruitNum;

    private boolean isCompleted;

    private Frequency frequency;

    private String writer;

    private List<UserResponseDto> users;

}
