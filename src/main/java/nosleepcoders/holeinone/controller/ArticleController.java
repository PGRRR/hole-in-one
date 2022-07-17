package nosleepcoders.holeinone.controller;

import nosleepcoders.holeinone.annotation.MemberSignInCheck;
import nosleepcoders.holeinone.domain.Article;
import nosleepcoders.holeinone.dto.ArticleSaveRequestDto;
import nosleepcoders.holeinone.service.ArticleService;
import nosleepcoders.holeinone.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final MemberService memberService;
    private final ArticleService articleService;

    @Autowired
    public ArticleController(MemberService memberService, ArticleService articleService) {
        this.memberService = memberService;
        this.articleService = articleService;
    }

    @GetMapping("")
    public String community(Model model) {
        List<Article> articleList = articleService.view();
        model.addAttribute("articleList", articleList);
        return "/article/community";
    }

    @MemberSignInCheck
    @GetMapping("/{id}/post")
    public String post(@PathVariable Long id, HttpSession session) {
        try {
            memberService.access(id, session);
            return "/article/post";
        } catch (Exception e) {
            return "redirect:/articles";
        }
    }
    @MemberSignInCheck
    @PostMapping("/{id}/post")
    public String post(@PathVariable(value = "id") Long member_id, HttpSession session, ArticleSaveRequestDto requestDto) {
        memberService.access(member_id, session);
        requestDto.setMember_id(member_id);
        articleService.post(requestDto);
        return "redirect:/articles";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Article> article = articleService.findById(id);
        model.addAttribute("article", article.get());
        return "/article/view";
    }
    @MemberSignInCheck
    @GetMapping("/{member_id}/{article_id}")
    public String delete(@PathVariable Long member_id, @PathVariable Long article_id) {
        articleService.delete(article_id, member_id);
        return "redirect:/articles";
    }
}
