package nosleepcoders.holeinone.shop.repository;

import nosleepcoders.holeinone.shop.domain.Reservation;
import nosleepcoders.holeinone.shop.domain.Store;
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
