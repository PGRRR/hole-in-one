package nosleepcoders.holeinone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nosleepcoders.holeinone.domain.Article;

@Getter
@Setter
@NoArgsConstructor
public class ArticleSaveRequestDto {
    private String article_title;
    private String article_text;
    private Long article_like;
    private Long member_id;

    @Builder
    public ArticleSaveRequestDto(String article_title, String article_text, Long article_like, Long member_id) {
        this.article_title = article_title;
        this.article_text = article_text;
        this.article_like = article_like;
        this.member_id = member_id;
    }

    public Article toEntity() {
        return Article.builder()
                .article_title(article_title)
                .article_text(article_text)
                .article_like(article_like)
                .member_id(member_id)
                .build();
    }
}
