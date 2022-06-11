//package nosleepcoders.holeinone.service;
//
//import nosleepcoders.holeinone.domain.Board;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BoardServiceTest {
//
//    BoardService boardService;
//    MemoryBoardRepository boardRepository;
//
//    @BeforeEach
//    public void beforeEach() {
//        boardRepository = new MemoryBoardRepository();
//        boardService = new BoardService(boardRepository);
//    }
//
//    @AfterEach
//    public void afterEach() {
//        boardRepository.clearStore();
//    }
//
//    @Test
//    void 게시글_저장() {
//        //given
//        Board board = new Board();
//        board.setTitle("spring");
//        //when
//        Long saveId = boardService.post(board);
//        //then
//        Board findBoard = boardService.findOne(saveId).get();
//        Assertions.assertThat(board.getTitle()).isEqualTo(findBoard.getTitle());
//    }
//
//    @Test
//    public void 중복_게시글_예외() {
//        //given
//        Board board1 = new Board();
//        board1.setTitle("spring");
//
//        Board board2 = new Board();
//        board2.setTitle("spring");
//
//        //when
//        boardService.post(board1);
//        IllegalStateException e = assertThrows(IllegalStateException.class, () -> boardService.post(board2));
//        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 제목 입니다.");
////        try {
////            boardService.post(board2);
////            fail();
////        } catch (IllegalStateException e) {
////            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 제목 입니다.");
////        }
//        //then
//    }
//
//    @Test
//    void findBoards() {
//    }
//
//    @Test
//    void findOne() {
//    }
//}