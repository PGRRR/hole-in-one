package nosleepcoders.holeinone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class BoardController {
    @GetMapping("")
    public String board() {
        return "/board/list";
    }
    @GetMapping("/view")
    public String view() {
        return "/board/view";
    }
    @GetMapping("/edit")
    public String edit() {
        return "/board/edit";
    }
    @GetMapping("/write")
    public String write() {
        return "/board/write";
    }

}
