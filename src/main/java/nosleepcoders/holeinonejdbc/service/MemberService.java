package nosleepcoders.holeinonejdbc.service;

import nosleepcoders.holeinonejdbc.domain.Member;
import nosleepcoders.holeinonejdbc.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * 회원 서비스 개발
 */
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        memberRepository.findByEmail(member.getEmail()) // 중복 회원 검증
                .ifPresent(m -> {
                    throw new IllegalStateException("중복 회원입니다.");
                });
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * email 존재 여부 확인
     */
    public void emailCheck(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) { // email 확인 검증
            System.out.println("EMAIL FAIL");
            throw new IllegalStateException("존재하지 않는 이메일입니다.");
        }
        System.out.println("EMAIL PASS");
    }

    /**
     * 로그인
     */
    public Optional<Member> login(String email, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) { // email 확인 검증
            System.out.println("EMAIL FAIL");
            throw new IllegalStateException("존재하지 않는 이메일입니다.");
        }
        if (!password.equals(member.get().getPassword())) { // 비밀번호 검증
            System.out.println("PASSWORD FAIL");
            throw new NullPointerException("비밀번호가 틀렸습니다.");
        }
        return member;
    }

    /**
     * 개인 정보 접근 검증
     */
    public Optional<Member> access(Long id, HttpSession session) {
        Object sessionAttribute = session.getAttribute("member");
        if (sessionAttribute == null) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        Member sessionMember = (Member) sessionAttribute;
        if (!id.equals(sessionMember.getId())) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        return memberRepository.findByEmail(sessionMember.getEmail());
    }

    /**
     * 개인 정보 수정
     */
    public void edit(Member updateMember){
        Optional<Member> member = memberRepository.findByEmail(updateMember.getEmail());
        memberRepository.update(updateMember);
        System.out.println("UPDATE MEMBER");
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
