package ru.anastasia.springcourse.models;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
//@Proxy(lazy=false)
@Table(name="folder")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfolder", nullable = false, unique = true)
    private Long id;

    @Column(name = "namefolder", length = 45)
    private String nameFolder;

    @JoinColumn(name = "iduserfk", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Users idUserFK;//

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idFolderFK")
    private List<Vocabulary> vocabularyList;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "idFolderFK")
    private Practice practice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public Users getIdUserFK() {
        return idUserFK;
    }//

    public void setIdUserFK(Users idUserFK) {
        this.idUserFK = idUserFK;
    }//

    public List<Vocabulary> getVocabularyList() {
        return vocabularyList;
    }

    public void setVocabularyList(List<Vocabulary> vocabularyList) {
        this.vocabularyList = vocabularyList;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }
}
