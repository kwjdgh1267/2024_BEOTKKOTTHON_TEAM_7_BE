package groomthon.studymate.repository;

import groomthon.studymate.entity.UserStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStudyRepository extends JpaRepository<UserStudy,Long> {
    List<UserStudy> findByStudyId(Long studyId);

}
