package ru.anastasia.springcourse.service;

import org.springframework.stereotype.Service;
import ru.anastasia.springcourse.models.Practice;
import ru.anastasia.springcourse.models.Progress;
import ru.anastasia.springcourse.repository.ProgressRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProgressService {

    final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public void add (int correct, int mistakes, Practice practice){
        Progress progress = new Progress();
        progress.setCorrect(correct);
        progress.setMistakes(mistakes);
        progress.setPractice(practice);
        progressRepository.saveAndFlush(progress);
    }

    public double getAverageResult (Practice practice){
        return progressRepository.getResult(practice.getId().intValue()); //practice.getId()
    }

    public void deleteResults (Practice practice) {
        progressRepository.deleteAllByIdPracticeFK(practice);
    }
}
