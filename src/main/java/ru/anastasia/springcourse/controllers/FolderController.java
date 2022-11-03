package ru.anastasia.springcourse.controllers;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.service.FolderService;

import javax.transaction.Transactional;

@Controller
@Transactional
//@RequestMapping("/folder")
public class FolderController {

    private FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }



//    @GetMapping("/new")
//    public String createFolder(){
//        return "addFolder";
//    }

}
