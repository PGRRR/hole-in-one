package nosleepcoders.holeinone.article.repository;

import nosleepcoders.holeinone.article.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaArticleRepository implements ArticleRepository{

    private final EntityManager em;
    public JpaArticleRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    @Override
    public void delete(Long article_id, Long member_id) {
        Article article = em.find(Article.class, article_id);
        em.remove(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        Article article = em.find(Article.class, id);
        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public List<Article> findAll(Pageable pageable) {
        return em.createQuery("select a from articles as a order by a.article_id DESC", Article.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
