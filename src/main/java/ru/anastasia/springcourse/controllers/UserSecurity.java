package ru.anastasia.springcourse.controllers;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.service.FolderService;
import ru.anastasia.springcourse.service.UsersService;
import ru.anastasia.springcourse.service.VocabularyService;

@Component("userSecurity")
public class UserSecurity {
    private UsersService usersService;
    private FolderService folderService;
    private VocabularyService vocabularyService;

    public UserSecurity(UsersService usersService, FolderService folderService, VocabularyService vocabularyService) {
        this.usersService = usersService;
        this.folderService = folderService;
        this.vocabularyService = vocabularyService;
    }

     Users getUser () {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return usersService.getUser(userDetails.getUsername());
    }

    public boolean hasUserId(Long userId){
        return userId.equals(getUser().getId());
    }

    public boolean hasFolderId (Long folderId){
        return folderService.folderAccess(folderId, getUser());
    }

    public boolean hasVocabularyId (Long vocabularyId){
        return vocabularyService.vocabularyAccess(vocabularyId, getUser());
    }
}
