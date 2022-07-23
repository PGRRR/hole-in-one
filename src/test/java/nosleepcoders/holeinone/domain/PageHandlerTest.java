package nosleepcoders.holeinone.domain;

import nosleepcoders.holeinone.article.domain.PageHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageHandlerTest {
    @Test
    void 페이지내비테스트1() {
        // given
        PageHandler pageHandler = new PageHandler(250, 1);
        pageHandler.print();
        // when
        // then
        assertEquals(1, pageHandler.getBeginPage());
        assertEquals(10, pageHandler.getEndPage());
    }

    @Test
    void 페이지내비테스트2() {
        // given
        PageHandler pageHandler = new PageHandler(250, 11);
        pageHandler.print();
        // when
        // then
        assertEquals(11, pageHandler.getBeginPage());
        assertEquals(20, pageHandler.getEndPage());
    }

}