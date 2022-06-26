package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.Article;
import nosleepcoders.holeinonejdbc.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ArticleRepository 인터페이스의 JDBC 구현체
 */
@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final DataSource dataSource;
    @Autowired
    public JdbcArticleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }


    @Override
    public Article save(Article article) {
        String sql = "insert into articles(article_title, article_text, member_id) values (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, article.getArticle_title());
            pstmt.setString(2, article.getArticle_text());
            pstmt.setLong(3, article.getMember_id());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    article.setArticle_id(rs.getLong(1));
                } else {
                    throw new SQLException("id 조회 실패");
                }
                return article;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Long delete(Long id) {
        String sql = "delete from articles where article_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            return id;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "select * from articles where article_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Article article = new Article();
                    article.setArticle_id(rs.getLong("article_id"));
                    article.setArticle_title(rs.getString("article_title"));
                    article.setArticle_text(rs.getString("article_text"));
                    article.setMember_id(rs.getLong("member_id"));
                    return Optional.of(article);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        String sql = "select * from articles where article_title = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Article article = new Article();
                    article.setArticle_id(rs.getLong("article_id"));
                    article.setArticle_title(rs.getString("article_title"));
                    article.setArticle_text(rs.getString("article_text"));
                    article.setMember_id(rs.getLong("member_id"));
                    return Optional.of(article);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select * from articles";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (rs.next()) {
                    Article article = new Article();
                    article.setArticle_id(rs.getLong("article_id"));
                    article.setArticle_title(rs.getString("article_title"));
                    article.setArticle_text(rs.getString("article_text"));
                    article.setMember_id(rs.getLong("member_id"));
                    articles.add(article);
                }
                return articles;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
