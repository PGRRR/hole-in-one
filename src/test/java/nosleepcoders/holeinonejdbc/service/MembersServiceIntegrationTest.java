package nosleepcoders.holeinonejdbc.service;

import nosleepcoders.holeinonejdbc.domain.Members;
import nosleepcoders.holeinonejdbc.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MembersServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

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
