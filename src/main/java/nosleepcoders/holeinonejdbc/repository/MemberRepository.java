package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.Members;

import java.util.List;
import java.util.Optional;

/**
 * 회원 리포지토리 인터페이스
 */
public interface MemberRepository {

    Members save(Members members);

    Members update(Members members);

    Optional<Members> findById(Long id);

    Optional<Members> findByEmail(String email);

    List<Members> findAll();
}
