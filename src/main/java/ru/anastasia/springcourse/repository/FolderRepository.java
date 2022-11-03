package ru.anastasia.springcourse.repository;

import org.hibernate.query.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Users;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    //Page<Folder> findAllByIdUserFK(long udUserFK, Pageable pageable); //
    List<Folder> findAllByIdUserFK(Users users);
    Boolean existsByIdAndIdUserFK(Long id, Users users);


    @Modifying
    @Query("update Folder f set f.nameFolder = :nameFolder where f.id = :id")
    void setNewFolderName(@Param("nameFolder") String name, @Param("id") Long id);

//    @Modifying
//    @Query(value = "insert into Folder (nameFolder, idUserFK) values (:insertName, :insertId)", nativeQuery = true)
//    void addNewFolder(@Param("insertName") String name, @Param("insertId") Long id);

}
