//package nosleepcoders.holeinone.service;
//
//<<<<<<<< HEAD:src/test/java/nosleepcoders/holeinone/service/MemberServiceIntegrationTest.java
//import nosleepcoders.holeinone.domain.Member;
//import nosleepcoders.holeinone.repository.MemberRepository;
//========
//import nosleepcoders.holeinonejdbc.domain.Members;
//import nosleepcoders.holeinonejdbc.repository.MemberRepository;
//>>>>>>>> origin/main:src/test/java/nosleepcoders/holeinone/service/MembersServiceIntegrationTest.java
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//
//@SpringBootTest
//@Transactional
//public class MembersServiceIntegrationTest {
//    @Autowired MemberService memberService;
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    void 회원가입() {
//        //given
//        Members members = new Members();
//        members.setEmail("spring");
//        members.setPassword("spring");
//        members.setName("spring");
//
//        //when
//        Long saveId = memberService.join(members);
//
//        //then
//        Members findMembers = memberService.findOne(saveId).get();
//        Assertions.assertThat(members.getEmail()).isEqualTo(findMembers.getEmail());
//    }
//
//}
