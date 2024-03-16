package groomthon.studymate.entity.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public enum Frequency {
    ONE("한번"),TWO("두번"),THREE("세번"),FOURPLUS("네번 이상");

    private String krName;
    Frequency(String krName){
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
    public static Frequency fromJson(String value) {
        for (Frequency frequency : Frequency.values()) {
            if (frequency.krName.equals(value)) {
                return frequency;
            }
        }
        throw new IllegalArgumentException("Invalid Frequency value: " + value);
    }
}
