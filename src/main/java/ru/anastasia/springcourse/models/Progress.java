package ru.anastasia.springcourse.models;
import javax.persistence.*;

@Entity
@Table(name="progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprogress", nullable = false, unique = true)
    private Long id;

    @Column(name = "correct")
    private int correct;

    @Column(name = "mistakes")
    private int mistakes;

    @JoinColumn(name = "idpracticefk", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Practice idPracticeFK;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public Practice getPractice() {
        return idPracticeFK;
    }

    public void setPractice(Practice idPracticeFK) {
        this.idPracticeFK = idPracticeFK;
    }
}
