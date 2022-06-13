package nosleepcoders.holeinonejdbc.service;

import nosleepcoders.holeinonejdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class MemberServiceTest {
    MemberService memberService;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setEmail("spring");
        member.setPassword("spring");
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getEmail()).isEqualTo(findMember.getEmail());
    }

}
