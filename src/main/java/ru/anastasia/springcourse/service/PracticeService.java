package ru.anastasia.springcourse.service;

import org.springframework.stereotype.Service;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Practice;
import ru.anastasia.springcourse.repository.PracticeRepository;

import javax.transaction.Transactional;
import java.util.Calendar;

@Service
@Transactional
public class PracticeService {

    final PracticeRepository practiceRepository;

    public PracticeService(PracticeRepository practiceRepository) {
        this.practiceRepository = practiceRepository;
    }

    public Boolean checkIfExists (Folder folder){
        return practiceRepository.existsByIdFolderFK(folder);
    }

    public Practice getPractice (Folder folder){
        return practiceRepository.getPracticeByIdFolderFK(folder);
    }

    public void add (Practice practice){
        practiceRepository.saveAndFlush(practice);
    }

    public void delete (Practice practice) {
        practiceRepository.delete(practice);
    }
}
