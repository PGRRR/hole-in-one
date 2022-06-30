package nosleepcoders.holeinone.controller;

import nosleepcoders.holeinone.domain.Member;
import nosleepcoders.holeinone.dto.MemberUpdateDto;
import nosleepcoders.holeinone.repository.MemberRepository;
import nosleepcoders.holeinone.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }


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
            session.setAttribute("members", memberService.login(email, password).get()); // 로그인 검증
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
            MemberUpdateDto memberUpdateDto = MemberService.memberUpdateDto(member.get());
            model.addAttribute("members", memberUpdateDto);
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
    public String update(@PathVariable Long id, MemberUpdateDto memberUpdateDto, HttpSession session, Model model) {
        try {
            memberService.access(id, session);
            Member editedMember = memberService.edit(id, memberUpdateDto);
            model.addAttribute("members", memberUpdateDto);
            model.addAttribute("update", "pass");
            session.removeAttribute("members");
            session.setAttribute("members", editedMember);
            return "/member/memberUpdate";
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/signOut")
    public String logOut(HttpSession session) {
        session.removeAttribute("members");
        return "redirect:/";
    }

    @GetMapping("/kakao")
    public String kakao(@RequestParam(value = "code", required = false) String code) {
        System.out.println("###" + code);
        String access_Token = memberService.getAccessToken(code);
        HashMap<String, Object> userInfo = memberService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        System.out.println("###email#### : " + userInfo.get("email"));
        System.out.println("###age#### : " + userInfo.get("age"));
        System.out.println("###id#### : " + userInfo.get("id"));
        return "/index";
    }

    @Transactional
    @GetMapping("/test")
    public ResponseEntity<Member> test(Member member) {
        return ResponseEntity.ok().body(memberRepository.save(member));
    }
}

