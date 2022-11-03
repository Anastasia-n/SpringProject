package ru.anastasia.springcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.anastasia.springcourse.models.Practice;
import ru.anastasia.springcourse.models.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @Query(value = "select AVG(CAST(p.correct AS DECIMAL(2,1))/5)*100 " +
            "from Progress p where p.idpracticefk = :practice " +
            "group by p.idpracticefk", nativeQuery = true)
    double getResult(@Param("practice") int practice);


    void deleteAllByIdPracticeFK(Practice practice);
}
