package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Member;
import nosleepcoders.holeinone.dto.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

/**
 * 회원 리포지토리 인터페이스
 */
public interface MemberRepository {

    Member save(Member member);

    Member update(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();
}
