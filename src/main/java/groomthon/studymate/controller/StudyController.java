package groomthon.studymate.controller;

import groomthon.studymate.dto.StudyRequestDto;
import groomthon.studymate.dto.StudyResponseDto;
import groomthon.studymate.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    //스터디 작성
    @PostMapping("/user/study/write")
    public StudyResponseDto createStudy(Authentication authentication, @RequestBody StudyRequestDto studyRequestDto){
        return studyService.createStudy(authentication,studyRequestDto);
    }
    //스터디 전체 조회
//    @GetMapping("/study")

    //스터디 참가

}
