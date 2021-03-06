package nosleepcoders.holeinone.member.repository;

import nosleepcoders.holeinone.member.domain.Member;

import java.util.List;
import java.util.Optional;

/**
 * 회원 리포지토리 인터페이스
 */
public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();

    void delete(Member member);
}
