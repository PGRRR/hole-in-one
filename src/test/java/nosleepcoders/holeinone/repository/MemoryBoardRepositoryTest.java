package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryBoardRepositoryTest {

    MemoryBoardRepository repository = new MemoryBoardRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Board board = new Board();
        board.setTitle("spring");
        repository.save(board);
        Board result = repository.findById(board.getId()).get();
        Assertions.assertThat(board).isEqualTo(result);
    }

    @Test
    public void findByTitle() {
        Board board1 = new Board();
        board1.setTitle("spring1");
        repository.save(board1);

        Board board2 = new Board();
        board2.setTitle("spring2");
        repository.save(board2);

        Board result = repository.findByTitle("spring1").get();

        Assertions.assertThat(result).isEqualTo(board1);

    }
    @Test
    public void findAll () {
        Board board1 = new Board();
        board1.setTitle("spring1");
        repository.save(board1);

        Board board2 = new Board();
        board2.setTitle("spring2");
        repository.save(board2);

        List<Board> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
