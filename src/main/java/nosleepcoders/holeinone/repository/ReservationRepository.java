package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Reservation;
import nosleepcoders.holeinone.domain.Store;

import java.util.List;
import java.util.Optional;

/**
 * 예약 리포지토리 인터페이스
 */
public interface ReservationRepository {

    Reservation save(Reservation reservation);

    String delete(String number);

    Optional<Reservation> findById(Long id);

    Optional<Reservation> findByNumber(String number);

    List<Reservation> findNumberByMemberId(Long id);

    List<Store> findStoreByMemberId(Long id);
}
