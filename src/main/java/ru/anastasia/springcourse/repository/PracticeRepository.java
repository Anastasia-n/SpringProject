package ru.anastasia.springcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Practice;


public interface PracticeRepository extends JpaRepository<Practice, Long> {

    Practice getPracticeByIdFolderFK (Folder folder);
    Boolean existsByIdFolderFK (Folder folder);

}
