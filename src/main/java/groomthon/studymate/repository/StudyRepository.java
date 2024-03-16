package groomthon.studymate.repository;

import groomthon.studymate.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study,Long> {

    @Query("select s from Study s where s.complete= :complete")
    List<Study> findAllByComp(@Param("complete") boolean complete);

    List<Study> findAllBy();

}
