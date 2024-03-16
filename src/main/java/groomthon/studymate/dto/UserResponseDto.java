package groomthon.studymate.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {//조회를 위한 것임
    private Long id;
    private String name;
    private String email;
    private String picture;
}
