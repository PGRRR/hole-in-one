package nosleepcoders.holeinone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "articles")
public class Article {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long article_id;

    @Column(nullable = false)
    private String article_title;

    @Column(nullable = false)
    private String article_text;

    @Column(nullable = false)
    private Long article_like;

    @Column(nullable = false)
    private Long member_id;
}
