package ru.anastasia.springcourse.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.repository.UsersRepository;

@Service("userDetailsServiceImpl")
public class UsersDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UsersDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users users = usersRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("Логин не найден"));
        return SecurityUsers.fromUser(users);
    }
}
