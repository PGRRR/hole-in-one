package nosleepcoders.holeinone.service;

import nosleepcoders.holeinone.domain.Article;
import nosleepcoders.holeinone.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 게시글 서비스 개발
 */
@Transactional(readOnly = true)
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    @Transactional
    public void post(Article article) {
        articleRepository.save(article);
    }

    @Transactional
    public void delete(Article article) {
        articleRepository.delete(article.getArticle_id());
    }

    @Transactional
    public List<Article> view() {
        return articleRepository.findAll();
    }

    @Transactional
    public Optional<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    @Transactional
    public Optional<Article> findById(Long id) { return articleRepository.findById(id);}
}
