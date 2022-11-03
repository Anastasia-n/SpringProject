package ru.anastasia.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "surname",required = false) String surname,
                            Model model){
        //System.out.println(name + " " + surname);
        model.addAttribute("message",name + " "  + surname);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage(){
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String calculate(@RequestParam("a") double a,
                            @RequestParam("b") double b,
                            @RequestParam("action") String action,
                            Model model) {
        double res = 0;
            switch (action) {
                case "multiplication":
                    res=a*b;
                    break;
                case "addition":
                    res=a + b;
                    break;
                case "subtraction":
                    res=a - b;
                    break;
                case "division":
                    res= a / b;
                    break;
                default:
                    res = 0;
                    break;
            }
        model.addAttribute("result",res);   
        return "first/calculator";
    }
}
