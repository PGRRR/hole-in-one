package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaArticleRepository implements ArticleRepository{
    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public List<Article> findAll() {
        return null;
    }
}
