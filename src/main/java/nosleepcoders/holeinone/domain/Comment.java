package nosleepcoders.holeinone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 댓글 엔티티 객체
 */
@Getter
@NoArgsConstructor
@Entity(name = "comments")
public class Comment {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long comment_id;

    @Column(nullable = false)
    private String comment_text;

    @Column(nullable = false)
    private Long article_id;

    @Column(nullable = false)
    private Long member_id;
}
