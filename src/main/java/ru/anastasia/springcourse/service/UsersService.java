package ru.anastasia.springcourse.service;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Role;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.repository.UsersRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UsersService {

    final
    UsersRepository usersRepository;

    public UsersService(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }

    public boolean save (Users user){
        Boolean check = usersRepository.findByLogin(user.getLogin()).isPresent();
        if(check) {
            return false;
        }
        user.setRole(Role.USER);
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
        usersRepository.save(user);
        return true;
    }

    public Users getUser(String username){
        return usersRepository.findByLogin(username).orElseThrow((() ->
                new UsernameNotFoundException("Логин не найден")));
    }

    public void setNewName(Users user){
        usersRepository.setNewUserName(user.getName(), user.getId());
    }
}
