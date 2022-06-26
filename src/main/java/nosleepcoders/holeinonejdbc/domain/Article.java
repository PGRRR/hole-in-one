package nosleepcoders.holeinonejdbc.domain;

public class Article {
    private Long article_id;
    private String article_title;
    private String article_text;
    private Long article_like;
    private Long member_id;

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public void setArticle_text(String article_text) {
        this.article_text = article_text;
    }

    public void setArticle_like(Long article_like) {
        this.article_like = article_like;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public String getArticle_text() {
        return article_text;
    }

    public Long getArticle_like() {
        return article_like;
    }

    public Long getMember_id() {
        return member_id;
    }
}
