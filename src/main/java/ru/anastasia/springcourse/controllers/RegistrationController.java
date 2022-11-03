package ru.anastasia.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.service.UsersService;

@Controller
public class RegistrationController {

    private UsersService usersService;

    public RegistrationController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new Users());
        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") Users users, Model model){
        if(!usersService.save(users)) {
            model.addAttribute("registrationError",
                    "Пользователь с таким логином уже существует");
            return "/registration";
        }
        model.addAttribute("registrationSuccess",
                "Пользователь успешно зарегистрирован");
        return "/login";
    }
}
