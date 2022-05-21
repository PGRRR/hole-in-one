package nosleepcoders.holeinone.controller;

import nosleepcoders.holeinone.domain.Member;
import nosleepcoders.holeinone.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CreateController {
//    private List<Member> memberList = new ArrayList<>();

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member")
    public String member() {
        return "/member/member";
    }

    @PostMapping("/member/create")
    public String create(Member member, Model model) {
        model.addAttribute("userId", member.getUserId());
//        memberList.add(member); // List<Member>
        memberRepository.save(member);
        return "/member/create";
    }

    @GetMapping("/member/list")
    public String list(Model model) {
//        model.addAttribute("memberList", memberList); // List<Member>
        model.addAttribute("memberList", memberRepository.findAll());
        return "/member/list";
    }
}
