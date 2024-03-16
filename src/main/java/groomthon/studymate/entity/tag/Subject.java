package groomthon.studymate.entity.tag;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Subject {

    LANGUAGE("어학"),CERTIFICATE("자격증"),MAJOR("전공"),ETC("기타");

    private String krName;
    Subject(String krName){
        this.krName=krName;
    }
    public String getKrName(){
        return krName;
    }

    @JsonValue
    public String toJson() {
        return krName;
    }

    @JsonCreator
    public static Subject fromJson(String value) {
        for (Subject subject : Subject.values()) {
            if (subject.krName.equals(value)) {
                return subject;
            }
        }
        throw new IllegalArgumentException("Invalid Subject value: " + value);
    }
}
