package ru.anastasia.springcourse.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.service.FolderService;
import ru.anastasia.springcourse.service.UsersService;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("")
public class MainController {

    private FolderService folderService;
    private UsersService usersService;

    public MainController(FolderService folderService, UsersService usersService) {
        this.folderService = folderService;
        this.usersService = usersService;
    }

    //private Users idUser = null;
    //@ModelAttribute("getUser")
    private Users users;
     private Users getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return usersService.getUser(userDetails.getUsername());
    }

    @GetMapping("/home") // главная страница
    public String mainPage(@AuthenticationPrincipal UserDetails currentUser, Model model){//@AuthenticationPrincipal UserDetails currentUser,
        users = usersService.getUser(currentUser.getUsername());
        model.addAttribute("folders",folderService.folders(users)); //user.getUsername() //folderService.folders(getUser().getLogin())
        //idUser = usersService.getUser(user.getUsername());
        model.addAttribute("user",users); //логин user.getUsername()
        return "mainPage";
    }

    @GetMapping("/home/{id}/edit") // Изменить имя пользователя
    public String editNameForm(@ModelAttribute("user") Users users){
        return "editUserName";
    }

    @PostMapping("/home/{id}/edit") // Изменить имя пользователя
    public String editName(@ModelAttribute("user") Users users) {
        usersService.setNewName(users);
        return "redirect:/home";
    }

    @GetMapping("/home/{id}/folder/new") //Добавить новый модуль
    public String addFolderForm(Model model){
        model.addAttribute("folder", new Folder());
        return "addFolder";
    }

    @PostMapping("/home/folder/new") //Добавить новый модуль
    public String addFolder(@ModelAttribute("folder") Folder folder){
        //folderService.add(folder,getUser().getId());
        folder.setIdUserFK(users);
        folderService.add(folder);
        return "redirect:/home";
    }

    @GetMapping("folder/{id}/edit") // Изменить названия модуля
    // public String editForm(@PathVariable("id") Long id, Model model){
    public String editFolderForm(@ModelAttribute("folder") Folder folder){
        //model.addAttribute("folder",folderService.getFolder(id));
        //  System.out.println(folderService.getFolder(id));
        return "editNameFolder";
    }

    @PostMapping("folder/{id}/edit") // Изменить название модуля
    public String editFolder (@ModelAttribute("folder") Folder folder){
        folderService.save(folder);
        return "redirect:/home";
    }

    @GetMapping("/folder/{id}/delete") //удалить модуль
    public String deleteFolderWarning(@PathVariable("id") Long id, Model model){
        System.out.println(folderService.getFolder(id).getNameFolder());
        model.addAttribute("folderToDelete", folderService.getFolder(id));
        return "deleteFolder";
    }

    @PostMapping("/folder/{id}/delete") //удалить модуль
    public String deleteFolder(@ModelAttribute("folder") Folder folder){
        folderService.delete(folder);
        return "redirect:/home";
    }

}
