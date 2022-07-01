package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Reservation;
import nosleepcoders.holeinone.domain.Store;
import nosleepcoders.holeinone.repository.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaReservationRepository implements ReservationRepository {
    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public void delete(Long number) {}

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> findByNumber(String number) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> findNumberByMemberId(Long id) {
        return null;
    }

    @Override
    public List<Store> findStoreByMemberId(Long id) {
        return null;
    }
}
