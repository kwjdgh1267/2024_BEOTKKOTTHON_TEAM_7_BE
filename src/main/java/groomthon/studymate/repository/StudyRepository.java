package groomthon.studymate.repository;

import groomthon.studymate.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study,Long> {

}
