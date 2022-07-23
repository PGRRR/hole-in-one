package nosleepcoders.holeinone.article.service;

import lombok.RequiredArgsConstructor;
import nosleepcoders.holeinone.article.domain.Article;
import nosleepcoders.holeinone.article.dto.ArticleSaveRequestDto;
import nosleepcoders.holeinone.article.repository.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 서비스 개발
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public void post(ArticleSaveRequestDto requestDto) {
        articleRepository.save(requestDto.toEntity());
    }

    @Transactional
    public void delete(Long article_id, Long member_id) {
        articleRepository.delete(article_id, member_id);
    }

    @Transactional
    public List<Article> view() {
        Sort sort = Sort.by("article_id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        return articleRepository.findAll(pageable);
    }

    @Transactional
    public Optional<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    @Transactional
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }
}
