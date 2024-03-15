package groomthon.studymate.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    MENTEE("ROLE_MENTEE", "멘티"),
    MENTOR("ROLE_MENTOR", "멘토");

    private final String key;
    private final String title;
}