package nosleepcoders.holeinonejdbc.service;

import nosleepcoders.holeinonejdbc.domain.Members;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class MembersServiceTest {
    MemberService memberService;

    @Test
    void 회원가입() {
        //given
        Members members = new Members();
        members.setEmail("spring");
        members.setPassword("spring");
        members.setName("spring");

        //when
        Long saveId = memberService.join(members);

        //then
        Members findMembers = memberService.findOne(saveId).get();
        Assertions.assertThat(members.getEmail()).isEqualTo(findMembers.getEmail());
    }

}
