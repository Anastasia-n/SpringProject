package ru.anastasia.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.service.UsersService;

@Controller
public class HomeController {

    final
    UsersService userService;

    public HomeController(UsersService userService) {
        this.userService = userService;
    }

    //@ResponseBody
    @RequestMapping("/me")
    public String home(Model model){
        Users user = userService.getUser("Anastasia");
        model.addAttribute("name", user.getName() + " "+ user.getPassword());
        //model.addAttribute("smth",user.getPassword());
//        System.out.println(user.getName());
//        System.out.println(user.getPassword());
        return "/hello_world";
    }

}
