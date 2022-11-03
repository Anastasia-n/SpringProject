package ru.anastasia.springcourse.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.repository.FolderRepository;
import ru.anastasia.springcourse.repository.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FolderService {

    final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    //    public Page<Folder> findPaginated(){
//        Pageable pageable1 = PageRequest.of(0,5);
//        Page<Folder> page = folderRepository.findAllByIdUserFK(1,pageable1);
//        return page;
//    }
    public List<Folder> folders(Users users){
        //return folderRepository.findAllByIdUserFK_Login(login);
        return folderRepository.findAllByIdUserFK(users);
    }

    public Folder getFolder(Long id){
        return folderRepository.getById(id);
    }

    public void save(Folder folder){
        folderRepository.setNewFolderName(folder.getNameFolder(), folder.getId());
    }

    public void add(Folder folder){
        //folder.setIdUserFK(users);
        //System.out.println(users.getId());
        folderRepository.saveAndFlush(folder);
        //folderRepository.addNewFolder(folder.getNameFolder(), id);
    }

    public void delete(Folder folder){
        folderRepository.delete(folder);
    }

    public boolean folderAccess(Long id, Users users){
        return folderRepository.existsByIdAndIdUserFK(id,users);
    }
}
