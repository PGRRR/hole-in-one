package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BoardRepository {
    Board save(Board board);
    Optional<Board> findById(Long id);
    Optional<Board> findByTitle(String title);
    List<Board> findAll();
}
