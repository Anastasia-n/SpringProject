package ru.anastasia.springcourse.models;
import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="practice")
public class Practice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpractice", nullable = false, unique = true)
    private Long id;

    @Column(name = "numberofpractices")
    private int numberOfPractices;

    @Column(name = "firstpracticedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar firstPracticeDate;

    @Column(name = "lastpracticedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastPracticeDate;

    @Column(name = "repetitionstage")
    private int repetitionStage;

    @JoinColumn(name = "idfolderfk", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Folder idFolderFK;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idPracticeFK")
    private List<Progress> progressList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfPractices() {
        return numberOfPractices;
    }

    public void setNumberOfPractices(int numberOfPractices) {
        this.numberOfPractices = numberOfPractices;
    }

    public Calendar getFirstPracticeDate() {
        return firstPracticeDate;
    }

    public void setFirstPracticeDate(Calendar firstPracticeDate) {
        this.firstPracticeDate = firstPracticeDate;
    }

    public Calendar getLastPracticeDate() {
        return lastPracticeDate;
    }

    public void setLastPracticeDate(Calendar lastPracticeDate) {
        this.lastPracticeDate = lastPracticeDate;
    }

    public int getRepetitionStage() {
        return repetitionStage;
    }

    public void setRepetitionStage(int repetitionStage) {
        this.repetitionStage = repetitionStage;
    }

    public Folder getIdFolderFK() {
        return idFolderFK;
    }

    public void setIdFolderFK(Folder idFolderFK) {
        this.idFolderFK = idFolderFK;
    }

    public List<Progress> getProgressList() {
        return progressList;
    }

    public void setProgressList(List<Progress> progressList) {
        this.progressList = progressList;
    }
}
