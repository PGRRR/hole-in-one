package nosleepcoders.holeinonejdbc.controller;

import nosleepcoders.holeinonejdbc.domain.Members;
import nosleepcoders.holeinonejdbc.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
class RestApiController {
    private final MemberRepository memberRepository;

    @Autowired
    RestApiController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @GetMapping("/rest")
    public Members member() {
        return memberRepository.findById(1L).get();
    }
}
