package nosleepcoders.holeinonejdbc.controller;

import nosleepcoders.holeinonejdbc.domain.Member;
import nosleepcoders.holeinonejdbc.repository.MemberRepository;
import nosleepcoders.holeinonejdbc.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/members")
public class MemberController {


    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    private MemberRepository memberRepository;

    /**
     * email 확인 POST 요청
     */
    @PostMapping("")
    public String emailVerify(String email, Model model) {
        try {
            memberService.emailCheck(email); // email 존재 여부 확인
            model.addAttribute("email", email);
            model.addAttribute("verify", "pass");
            return "/member/signIn";
        } catch (IllegalStateException e) {
            model.addAttribute("email", email);
            return "/member/signUp";
        }
    }

    @GetMapping("/signIn")
    public String signIn() {
        return "/member/signIn";
    }

    /**
     * 로그인 POST 요청
     */
    @PostMapping("/signIn")
    public String verify(String email, String password, HttpSession session, Model model) {
        try {
            session.setAttribute("member", memberService.login(email, password).get()); // 로그인 검증
            System.out.println("ALL PASS");
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("email", email);
            return "/member/signUp";
        } catch (NullPointerException e) {
            model.addAttribute("email", email);
            model.addAttribute("verify", "fail");
            return "/member/signIn";
        }
    }

    @GetMapping("/signUp")
    public String create() {
        return "/member/signUp";
    }

    /**
     * 회원 가입 POST 요청
     */
    @PostMapping("/signUp")
    public String member(Member member, Model model) {
        try {
            memberService.join(member);
            System.out.println("SAVE MEMBER");
            return "redirect:/";
        } catch (IllegalStateException e) {
            System.out.println("OVERLAP EMAIL FAIL");
            model.addAttribute("email", member.getEmail());
            model.addAttribute("verify", "fail");
            return "/member/signUp";
        }
    }

    /**
     * 개인 정보 GET 요청
     */
    @GetMapping("/{id}/profile")
    public String myAccount(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Optional<Member> member = memberService.access(id, session);
            model.addAttribute("member", member.get());
            model.addAttribute("update", "fail");
            return "/member/memberUpdate";
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
    }

    /**
     * 개인 정보 수정 POST 요청
     */
    @PostMapping("/{id}/profile")
    public String update(@PathVariable Long id, Member updateMember, HttpSession session, Model model) {
        try {
            memberService.access(id, session);
            memberService.edit(updateMember);
            model.addAttribute("update", "pass");
            return "/member/memberUpdate";
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/signOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("member");
        return "redirect:/";
    }
}

