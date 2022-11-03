package ru.anastasia.springcourse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.models.Vocabulary;

import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findAllByIdFolderFK(Folder folder);
    Boolean existsByIdAndIdFolderFK_IdUserFK(Long id, Users users);
    Page<Vocabulary> findAllByIdFolderFK (Folder folder, Pageable pageable); //pagination

    //@Query(value = "select v.idword, v.word, v.translation, v.context, v.idfolderfk from vocabulary v where v.idfolderfk = :folderID order by random()", nativeQuery = true)
  //  Page<Vocabulary> selectRandom (@Param("folderID") int id, Pageable pageable); //pagination
   //Page<Vocabulary> findAllByIdFolderFK (Folder folder, Pageable pageable); //pagination


}
