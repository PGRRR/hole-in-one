package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.Members;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Members save(Members members) {
        em.persist(members);
        return members;
    }

    @Override
    public Members update(Members members) {
        return null;
    }

    @Override
    public Optional<Members> findById(Long id) {
        Members members = em.find(Members.class, id);
        return Optional.ofNullable(members);
    }

    @Override
    public Optional<Members> findByEmail(String email) {
        List<Members> result = em.createQuery("select m from member m where m.email = :email", Members.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Members> findAll() {
        return em.createQuery("select m from Member m", Members.class)
                .getResultList();
    }
}
