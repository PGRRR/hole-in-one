package nosleepcoders.holeinonejdbc.controller;

import nosleepcoders.holeinonejdbc.domain.Article;
import nosleepcoders.holeinonejdbc.service.ArticleService;
import nosleepcoders.holeinonejdbc.service.MemberService;
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

    @GetMapping("/{id}/post")
    public String post(@PathVariable Long id, HttpSession session) {
        try {
            memberService.access(id, session);
            return "/article/post";
        } catch (Exception e) {
            return "redirect:/articles";
        }
    }

    @PostMapping("/{id}/post")
    public String post(@PathVariable Long id, HttpSession session, Article article) {
        try {
            memberService.access(id, session);
            article.setMember_id(id);
            articleService.post(article);
            return "redirect:/articles";
        } catch (Exception e) {
            return "redirect:/articles";
        }
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Article> article = articleService.findById(id);
        model.addAttribute("article", article.get());
        return "/article/view";
    }

    @GetMapping("/{member_id}/{article_id}")
    public String delete(@PathVariable Long member_id, @PathVariable Long article_id, Article article) {
        article.setId(article_id);
        article.setMember_id(member_id);
        articleService.delete(article);
        return "redirect:/articles";
    }
}
