package groomthon.studymate.controller;

import groomthon.studymate.dto.StudyRequestDto;
import groomthon.studymate.dto.StudyResponseDto;
import groomthon.studymate.service.StudyService;
import groomthon.studymate.service.UserStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final UserStudyService userStudyService;

    //스터디 작성
    @PostMapping("/user/study/write")
    public StudyResponseDto createStudy(Authentication authentication, @RequestBody StudyRequestDto studyRequestDto){
        return studyService.createStudy(authentication,studyRequestDto);
    }
    //스터디 마감 안된것 전체 조회(0이면 모집 안끝난것 1이면 끝난것)
    @GetMapping("/study")
    public List<StudyResponseDto> findAllStudy(){
        return studyService.findAllStudy(false);
    }
    //스터디 하나 조회
    @GetMapping("/study/{study_id}")
    public StudyResponseDto findOneStudy(@PathVariable("study_id") Long study_id){
        return studyService.findOneStudy(study_id);
    }

    //스터디 참가
    @GetMapping("/user/study/{study_id}/write")
    public String joinStudy(Authentication authentication, @PathVariable("study_id") Long study_id){
        return userStudyService.joinStudy(authentication,study_id);

    }

}
