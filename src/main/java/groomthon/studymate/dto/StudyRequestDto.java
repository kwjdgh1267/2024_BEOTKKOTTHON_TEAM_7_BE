package groomthon.studymate.dto;

import groomthon.studymate.entity.tag.Frequency;
import groomthon.studymate.entity.tag.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyRequestDto {

    private String title;

    private String contents;

    private Subject subject;

    private long recruitNum;

    private Frequency frequency;

}
