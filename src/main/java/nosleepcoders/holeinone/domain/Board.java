package nosleepcoders.holeinone.domain;

import javax.persistence.*;

@Table(name = "member")
@Entity
public class Board {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String title;
    private String article;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
