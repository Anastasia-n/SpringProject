package ru.anastasia.springcourse.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.anastasia.springcourse.models.Folder;
import ru.anastasia.springcourse.models.Practice;
import ru.anastasia.springcourse.models.Vocabulary;
import ru.anastasia.springcourse.service.FolderService;
import ru.anastasia.springcourse.service.PracticeService;
import ru.anastasia.springcourse.service.ProgressService;
import ru.anastasia.springcourse.service.VocabularyService;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Controller
@Transactional
public class VocabularyController {

    private FolderService folderService;
    private VocabularyService vocabularyService;
    private PracticeService practiceService;
    private ProgressService progressService;
    private Folder folder;
    private int correctAnswers = 0;
    private int mistakes = 0;
    private int currentPage = 0;


    public VocabularyController(FolderService folderService, VocabularyService vocabularyService,
                                PracticeService practiceService, ProgressService progressService) {
        this.folderService = folderService;
        this.vocabularyService = vocabularyService;
        this.practiceService = practiceService;
        this.progressService = progressService;
    }


    @GetMapping("/folder/{id}/vocabulary") //страница слов
    public String getVocabularyPage(@PathVariable Long id, Model model){
        clearCounters();
        folder = folderService.getFolder(id);
        folder.getNameFolder();
        model.addAttribute("showFolder",folder);
        model.addAttribute("words",vocabularyService.getVocabulary(folder));
        model.addAttribute("timeLeft", showTimeLeft(folder));
        return "/vocabulary/vocabularyPage";
    }

    @GetMapping("/vocabulary/{id}/new") //добавить слово
    public String addWordForm(Model model){
        model.addAttribute("newWord",new Vocabulary());
        return "/vocabulary/addWord";
    }

    @PostMapping("/vocabulary/new") //добавить слово
    public String addWord(@ModelAttribute("newWord") Vocabulary vocabulary){
        vocabulary.setIdFolderFK(folder);
        vocabularyService.add(vocabulary);
        return "redirect:/folder/" + folder.getId() + "/vocabulary";
    }

    @GetMapping("/vocabulary/{id}/delete") //удалить слово
    public String deleteVocabularyWarning(@PathVariable Long id, Model model){
        System.out.println(vocabularyService.getVocabulary(id).getWord());
        model.addAttribute("wordToDelete",vocabularyService.getVocabulary(id));
        return "/vocabulary/deleteWord";
    }

    @PostMapping("/vocabulary/{id}/delete") //удалить слово
    public String deleteVocabulary(@ModelAttribute Vocabulary vocabulary){
        vocabularyService.delete(vocabulary);
        return "redirect:/folder/" + folder.getId() + "/vocabulary";
    }

    @GetMapping("/vocabulary/{id}/edit") //редактировать слово
    public String editVocabularyForm(@PathVariable("id") Long id, Model model){
        System.out.println(vocabularyService.getVocabulary(id));
        model.addAttribute("wordToEdit",vocabularyService.getVocabulary(id));
        return "/vocabulary/editWord";
    }

    @PostMapping("/vocabulary/{id}/edit") // Изменить слово
     public String editVocabulary(@ModelAttribute("wordToEdit") Vocabulary vocabulary){
        vocabulary.setIdFolderFK(folder);
        vocabularyService.updateVocabulary(vocabulary);
//        System.out.println(folderService.getFolder(id));
//        model.addAttribute("folder",folderService.getFolder(id));
        return "redirect:/folder/" + folder.getId() + "/vocabulary";
    }

    @GetMapping("/learning") // изучить слова
    public String learnVocabulary (@PageableDefault(size = 1) Pageable pageable, Model model){ //direction = Sort.Direction.DESC)
        Page<Vocabulary> page = vocabularyService.getVocabularyForPagination(folder, pageable);
        model.addAttribute("page", page);
        model.addAttribute("back",folder.getId());
        model.addAttribute("counter",correctAnswers);
        model.addAttribute("currentPage",currentPage);
        return "/vocabulary/vocabularyLearning";
    }

    @PostMapping("/learning/check") // проверка слова
    public ModelAndView checkWord (@RequestParam("wordToCompare") String wordToCompare,
                             @RequestParam("wordToCheck") String wordToCheck,
                             @RequestParam("pageNumber") String pageNumber){
        if (wordToCompare.equals(wordToCheck))
            correctAnswers++;
        else
            mistakes++;
        currentPage++;
        return new ModelAndView("redirect:/learning?page=" + pageNumber +"&size=1");
    }

    @PostMapping("/learning/finish") // конец изучения
    public String finishLearning(@RequestParam("numberOfPages") String numberOfPages, Model model) {
        model.addAttribute("correct", correctAnswers);
        model.addAttribute("mistakes", mistakes);
        model.addAttribute("numberOfPages", numberOfPages);
        return "/vocabulary/learningResult";
    }

    @GetMapping("/learning/delete") // не сохранять результат
    public String deleteResults() {
        clearCounters();
        return "redirect:/folder/"+folder.getId()+"/vocabulary";
    }

    @GetMapping("/learning/save") // сохранить результат
    public String saveResults() {
        Practice practice = new Practice();
        if(practiceService.checkIfExists(folder)){
            practice = practiceService.getPractice(folder);
            practice.setNumberOfPractices(practice.getNumberOfPractices()+1);
            if(practice.getRepetitionStage() <= 10
                    && getTimeLeft(practice).isNegative()) {
                practice.setRepetitionStage(practice.getRepetitionStage()+1);
                practice.setFirstPracticeDate(Calendar.getInstance());
            }
            practice.setLastPracticeDate(Calendar.getInstance());
        }
        else {
            practice.setNumberOfPractices(1);
            practice.setFirstPracticeDate(Calendar.getInstance());
            practice.setLastPracticeDate(Calendar.getInstance());
            practice.setRepetitionStage(1);
            practice.setIdFolderFK(folder);
        }
        practiceService.add(practice);
        progressService.add(correctAnswers, mistakes, practice);
        clearCounters();
        return "redirect:/folder/"+folder.getId()+"/vocabulary";
    }

    public void clearCounters() {
        correctAnswers = 0;
        mistakes = 0;
        currentPage = 0;
    }

    public Duration getTimeLeft (Practice practice) { // получить время до следующего повторения
        Calendar calendarFirst = new GregorianCalendar();
        calendarFirst.setTime(practice.getFirstPracticeDate().getTime());
        Duration duration = Duration.ZERO;
        switch (practice.getRepetitionStage()){
            case 1:
                calendarFirst.add(Calendar.MINUTE, 2);
                duration = Duration.between(Calendar.getInstance().toInstant(), calendarFirst.toInstant());
                break;
            case 2:
                calendarFirst.add(Calendar.MINUTE, 10);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
            case 3:
                calendarFirst.add(Calendar.HOUR,1);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
            case 4:
                calendarFirst.add(Calendar.HOUR, 5);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
            case 5:
                calendarFirst.add(Calendar.DAY_OF_MONTH,1);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
            case 6:
                calendarFirst.add(Calendar.DAY_OF_MONTH,5);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
            case 7:
                calendarFirst.add(Calendar.DAY_OF_MONTH,25);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
            case 8:
                calendarFirst.add(Calendar.MONTH,4);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
            case 9:
                calendarFirst.add(Calendar.YEAR,2);
                duration = Duration.between(Calendar.getInstance().toInstant(),calendarFirst.toInstant());
                break;
        }
        return duration;
    }

    public String showTimeLeft (Folder folder){ // показать оставшееся время
        if(practiceService.checkIfExists(folder)) {
            Practice practice = practiceService.getPractice(folder);
            Duration duration = getTimeLeft(practice);
            if (duration.isNegative()){
                return "0 дней 0 ч. 0 мин. 0 с.";
            }
            return duration.toDaysPart() + " дн. " + duration.toHoursPart() +
                    " ч. " + duration.toMinutesPart() + " мин. " +
                    duration.toSecondsPart() + " с.";
        }
        return "нет данных";
    }

    @GetMapping("/learning/reset") // начать изучения заново
    public String counterReset () {
        return "/vocabulary/counterReset";
    }

    @GetMapping("/learning/resetting") // начать изучение заново (сброс отсчета)
    public String counterResetting () {
        if(practiceService.checkIfExists(folder)) {
            Practice practice = practiceService.getPractice(folder);
            practiceService.delete(practice);
        }
        return "redirect:/folder/"+folder.getId()+"/vocabulary";
    }

    @GetMapping("/learning/statistics") // показать статистику модуля
    public String showStatistics (Model model) {
        int number = 0;
        String lastLearning = "нет данных";
        double result = 0;
        if(practiceService.checkIfExists(folder)) {
            Practice practice = practiceService.getPractice(folder);
            if (practice.getLastPracticeDate() != null) {
                number = practice.getNumberOfPractices();
                Duration duration = Duration.between(practice.getLastPracticeDate().toInstant(), Calendar.getInstance().toInstant());
                lastLearning = duration.toDaysPart() + " дн. " + duration.toHoursPart() + " ч. " +
                        duration.toMinutesPart() + " мин. " + duration.toSecondsPart() + " c. назад";
                result = progressService.getAverageResult(practice);
            }
        }
        model.addAttribute("number",number);
        model.addAttribute("lastLearning", lastLearning);
        model.addAttribute("result", result);
        return "/vocabulary/vocabularyStatistics";
    }

    @GetMapping("/learning/statistics/delete") //сброс статистики
    public String deleteStatistics () {
        if(practiceService.checkIfExists(folder)) {
            Practice practice = practiceService.getPractice(folder);
            practice.setNumberOfPractices(0);
            practice.setLastPracticeDate(null);
            progressService.deleteResults(practice);
        }
        return "redirect:/folder/"+folder.getId()+"/vocabulary";
    }

}

