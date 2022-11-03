package ru.anastasia.springcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.anastasia.springcourse.models.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByLogin(String login);

    @Modifying
    @Query("update Users u set u.name = :nameUser where u.id = :id")
    void setNewUserName(@Param("nameUser") String name, @Param("id") Long id);

}
