package ru.anastasia.springcourse.models;
import javax.persistence.*;

@Entity
@Table(name="vocabulary")
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idword", nullable = false, unique = true)
    private Long id;

    @Column(name = "word", length = 255)
    private String word;

    @Column(name = "translation", length = 255)
    private String translation;

    @Column(name = "context", length = 255)
    private String context;

    @JoinColumn(name = "idfolderfk", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Folder idFolderFK;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Folder getIdFolderFK() {
        return idFolderFK;
    }

    public void setIdFolderFK(Folder idFolderFK) {
        this.idFolderFK = idFolderFK;
    }
}
