package nosleepcoders.holeinone.service;

import nosleepcoders.holeinone.domain.Board;
import nosleepcoders.holeinone.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * 게시글 저장
     */
    public Long post(Board board) {
        validateDuplicateBoard(board); // 중복 게시글 검증
        boardRepository.save(board);
        return board.getId();
    }

    private void validateDuplicateBoard(Board board) {
        boardRepository.findByTitle(board.getTitle())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 제목 입니다.");
                });
    }

    /**
     * 전체 게시글 조회
     */
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> findOne(Long boardId) {
        return boardRepository.findById(boardId);
    }
}

