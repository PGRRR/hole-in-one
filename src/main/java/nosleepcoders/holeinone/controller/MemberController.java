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

    @PostMapping("/email")
    public String emailVerify(String email, Model model) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            System.out.println("EMAIL FAIL");
            model.addAttribute("email", email);
            return "/member/signUp";
        }
        System.out.println("EMAIL PASS");
        model.addAttribute("email", email);
        model.addAttribute("verify", "pass");
        return "/member/signIn";
    }

    @PostMapping("/verify")
    public String verify(String email, String password, HttpSession session, Model model) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            System.out.println("EMAIL FAIL");
            model.addAttribute("email", email);
            return "/member/signUp";
        }
        if (!password.equals(member.getPassword())) {
            System.out.println("PASSWORD FAIL");
            model.addAttribute("email", email);
            model.addAttribute("verify", "fail");
            return "/member/signIn";
        }
        session.setAttribute("member", member);
        System.out.println("ALL PASS");
        return "/index";
    }

    @PostMapping("/welcome")
    public String member(Member member, Model model) {

        if (memberRepository.findByEmail(member.getEmail()) != null) {
            System.out.println("OVERLAP EMAIL FAIL");
            model.addAttribute("email", member.getEmail());
            return "/member/signUp";
        }
        System.out.println("SAVE MEMBER");
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
