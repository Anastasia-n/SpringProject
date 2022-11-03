package ru.anastasia.springcourse.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Users;
import ru.anastasia.springcourse.models.Vocabulary;
import ru.anastasia.springcourse.repository.VocabularyRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class VocabularyService {

    final VocabularyRepository vocabularyRepository;

    public VocabularyService(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    public List<Vocabulary> getVocabulary(Folder folder){
//        for (Vocabulary item : vocabularyRepository.findAllByIdFolderFK(folder)) {
//            System.out.println(item.getWord());
//        }
        return vocabularyRepository.findAllByIdFolderFK(folder);
    }

    public void add(Vocabulary vocabulary){
        vocabularyRepository.saveAndFlush(vocabulary);
    }

    public void delete (Vocabulary vocabulary){
        vocabularyRepository.delete(vocabulary);
    }

    public Vocabulary getVocabulary (Long id){
        return vocabularyRepository.getById(id);
    }

    public void updateVocabulary(Vocabulary vocabulary){
        vocabularyRepository.saveAndFlush(vocabulary);
    }

    public Page<Vocabulary> getVocabularyForPagination (Folder folder, Pageable pageable){
        //Pageable pageable = PageRequest.ofSize(2);
         return vocabularyRepository.findAllByIdFolderFK(folder, pageable);
        //return vocabularyRepository.selectRandom(folder.getId().intValue(), pageable);
    }

    public boolean vocabularyAccess(Long id, Users users) {
        return vocabularyRepository.existsByIdAndIdFolderFK_IdUserFK(id, users);
    }

}
