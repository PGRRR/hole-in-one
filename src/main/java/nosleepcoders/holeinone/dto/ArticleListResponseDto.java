package nosleepcoders.holeinone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nosleepcoders.holeinone.domain.Article;
import nosleepcoders.holeinone.domain.Member;

@Getter
@NoArgsConstructor
public class ArticleListResponseDto {
    private String name;
    private Long article_id;
    private String article_title;
    private Long article_like;

    // repository 를 통해 조회한 Entity 를 DTO 로 변환 용도
    public ArticleListResponseDto(Member member, Article article) {
        this.name = member.getName();
        this.article_id = article.getArticle_id();
        this.article_title = article.getArticle_title();
        this.article_like = article.getArticle_like();
    }
}
