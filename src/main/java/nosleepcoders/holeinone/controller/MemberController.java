package nosleepcoders.holeinone.controller;

import nosleepcoders.holeinone.domain.Member;
import nosleepcoders.holeinone.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signIn")
    public String signIn() {
        return "/member/signIn";
    }

    @PostMapping("/verify")
    public String verify(String userId, String password, HttpSession session, Model model) {
        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            System.out.println("MEMBER FAIL");
            return "redirect:/members/signIn";
        }
        if (!password.equals(member.getPassword())) {
            System.out.println("PASSWORD FAIL");
            return "redirect:/members/signIn";
        }
        session.setAttribute("member", member);
        model.addAttribute("name", userId);
        System.out.println("PASS");
        return "/index";
    }

    @PostMapping("/welcome")
    public String member(Member member, Model model) {

        memberRepository.save(member);
        model.addAttribute("name", member.getName());
        return "/member/welcome";
    }

    @GetMapping("/myAccount")
    public String update() {
        return "/member/memberUpdate";
    }

    @GetMapping("/signUp")
    public String create() {
        return "/member/signUp";
    }

    @GetMapping("/signOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("member");
        return "redirect:/";
    }
}
