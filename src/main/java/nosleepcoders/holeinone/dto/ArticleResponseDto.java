package nosleepcoders.holeinone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nosleepcoders.holeinone.domain.Article;
import nosleepcoders.holeinone.domain.Comment;
import nosleepcoders.holeinone.domain.Member;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {

    @Email
    private String email;

    private String name;

    private Long level;

    private Long article_id;

    private String article_title;

    private String article_text;

    private Long article_like;

    private String comment_text;

    // repository 를 통해 조회한 Entity 를 DTO 로 변환 용도
    public ArticleResponseDto(Member member, Article article, Comment comment) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.level = member.getLevel();
        this.article_id = article.getArticle_id();
        this.article_title = article.getArticle_title();
        this.article_text = article.getArticle_text();
        this.article_like = article.getArticle_like();
        this.comment_text = comment.getComment_text();
    }
}
