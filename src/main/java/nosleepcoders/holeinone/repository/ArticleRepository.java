package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 예약 리포지토리 인터페이스
 */
public interface ArticleRepository {

    Article save(Article article);

    void delete(Long article_id, Long member_id);

    Optional<Article> findById(Long id);

    Optional<Article> findByTitle(String title);

    List<Article> findAll(Pageable pageable);
}
