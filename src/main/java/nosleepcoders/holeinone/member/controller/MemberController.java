package nosleepcoders.holeinone.member.controller;

import lombok.RequiredArgsConstructor;
import nosleepcoders.holeinone.annotation.MemberSignInCheck;
import nosleepcoders.holeinone.member.domain.Member;
import nosleepcoders.holeinone.member.dto.MemberResponseDto;
import nosleepcoders.holeinone.member.dto.MemberSaveRequestDto;
import nosleepcoders.holeinone.member.dto.MemberUpdateRequestDto;
import nosleepcoders.holeinone.member.repository.MemberRepository;
import nosleepcoders.holeinone.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

//    @ExceptionHandler(Exception.class)
//    public String catcher(Exception ex) {
//        return "/handler/memberError";
//    }

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
        } catch (IllegalArgumentException e) {
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
            session.setAttribute("members", memberService.login(email, password));
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
    public String member(MemberSaveRequestDto memberSaveRequestDto, Model model) {
        try {
            memberService.join(memberSaveRequestDto);
            System.out.println("SAVE MEMBER");
            return "redirect:/";
        } catch (IllegalStateException e) {
            System.out.println("OVERLAP EMAIL FAIL");
            model.addAttribute("email", memberSaveRequestDto.getEmail());
            model.addAttribute("verify", "fail");
            return "/member/signUp";
        }
    }

    /**
     * 개인 정보 GET 요청
     */
    @MemberSignInCheck
    @GetMapping("/{id}/profile")
    public String myAccount(@PathVariable Long id, HttpSession session, Model model) {
        try {
            MemberResponseDto responseDto = memberService.access(id, session);
            model.addAttribute("members", responseDto);
            model.addAttribute("update", "fail");
            return "/member/memberUpdate";
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
    }

    /**
     * 개인 정보 수정 POST 요청
     */
    @MemberSignInCheck
    @PostMapping("/{id}/profile")
    public String update(@PathVariable Long id, MemberUpdateRequestDto memberUpdateRequestDto, HttpSession session, Model model) {
        try {
            memberService.access(id, session);
            Member editedMember = memberService.edit(id, memberUpdateRequestDto);
            model.addAttribute("members", memberUpdateRequestDto);
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
    public String kakao(@RequestParam(value = "code", required = false) String code, HttpSession session) {
        System.out.println("###" + code);
        String access_Token = memberService.getAccessToken(code);
        HashMap<String, Object> userInfo = memberService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        System.out.println("###email#### : " + userInfo.get("email"));
        System.out.println("###age#### : " + userInfo.get("age"));
        System.out.println("###id#### : " + userInfo.get("id"));
        try {
            session.setAttribute("members", memberService.login((String) userInfo.get("email"), String.valueOf(userInfo.get("id"))));
            System.out.println("ALL PASS");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            MemberSaveRequestDto memberSaveRequestDto = new MemberSaveRequestDto();
            memberSaveRequestDto.setEmail((String) userInfo.get("email"));
            memberSaveRequestDto.setName((String) userInfo.get("nickname"));
            memberSaveRequestDto.setPassword(String.valueOf(userInfo.get("id")));
            memberService.join(memberSaveRequestDto);
            return "redirect:/";
        }
    }

    @GetMapping("/withdraw/{id}")
    public String withdraw() {
        return "/member/memberWithdraw";
    }
    @MemberSignInCheck
    @PostMapping("/withdraw/{id}")
    public String delete(MemberSaveRequestDto memberSaveRequestDto, Model model) {
        member(memberSaveRequestDto, model);
        memberService.withdraw(memberSaveRequestDto);
        return "/member/memberWithdraw";
    }

    @Transactional
    @GetMapping("/test")
    public ResponseEntity<Member> test(Member member) {
        return ResponseEntity.ok().body(memberRepository.save(member));
    }
}

