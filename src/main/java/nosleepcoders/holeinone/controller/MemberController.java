package nosleepcoders.holeinone.controller;

import nosleepcoders.holeinone.domain.Member;
import nosleepcoders.holeinone.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("")
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

    @GetMapping("/signIn")
    public String signIn() {
        return "/member/signIn";
    }

    @PostMapping("/signIn")
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
        return "redirect:/";
    }

    @GetMapping("/signUp")
    public String create() {
        return "/member/signUp";
    }

    @PostMapping("/signUp")
    public String member(Member member, Model model) {

        if (memberRepository.findByEmail(member.getEmail()) != null) {
            System.out.println("OVERLAP EMAIL FAIL");
            model.addAttribute("email", member.getEmail());
            model.addAttribute("verify", "fail");
            return "/member/signUp";
        }
        System.out.println("SAVE MEMBER");
        memberRepository.save(member);
        return "redirect:/";
    }

    @GetMapping("/{id}/profile")
    public String myAccount(@PathVariable Long id, HttpSession session, Model model) {
        Object tmpMember = session.getAttribute("member");
        if (tmpMember == null) {
            return "redirect:/";
        }
        Member sessionMember = (Member)tmpMember;
        if (!id.equals(sessionMember.getId())) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        model.addAttribute("update", "fail");
        return "/member/memberUpdate";
    }

    @PostMapping("/{id}/profile")
    public String update(@PathVariable Long id, Member updateMember, HttpSession session, Model model){
        Object tmpMember = session.getAttribute("member");
        if (tmpMember == null) {
            return "redirect:/";
        }
        Member sessionMember = (Member)tmpMember;
        if (!id.equals(sessionMember.getId())) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        model.addAttribute("update", "pass");
        return "/member/memberUpdate";
    }

    @GetMapping("/signOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("member");
        return "redirect:/";
    }
}
