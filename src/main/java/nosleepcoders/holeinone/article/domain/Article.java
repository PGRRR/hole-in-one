package nosleepcoders.holeinone.article.domain;

import lombok.*;
import nosleepcoders.holeinone.member.domain.Member;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 게시글 엔티티 객체
 */
@Getter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity(name = "articles")
public class Article {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long article_id;

    @Column(nullable = false)
    private String article_title;

    @Column(nullable = false)
    private String article_text;

    @Column(nullable = false)
    private Long article_like;

    @Column(nullable = false)
    private Long member_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable=false, updatable=false)
    private Member member;

    @Builder
    public Article(Long article_id, String article_title, String article_text, Long article_like, Long member_id) {
        this.article_id = article_id;
        this.article_title = article_title;
        this.article_text = article_text;
        this.article_like = article_like;
        this.member_id = member_id;
    }
}
