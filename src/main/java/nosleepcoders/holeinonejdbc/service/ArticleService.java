package nosleepcoders.holeinonejdbc.service;

import nosleepcoders.holeinonejdbc.domain.Article;
import nosleepcoders.holeinonejdbc.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 서비스 개발
 */
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void post(Article article) {
        articleRepository.save(article);
    }

    public void delete(Article article) {
        articleRepository.delete(article.getId());
    }

    public List<Article> view() {
        return articleRepository.findAll();
    }

    public Optional<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    public Optional<Article> findById(Long id) { return articleRepository.findById(id);}
}
