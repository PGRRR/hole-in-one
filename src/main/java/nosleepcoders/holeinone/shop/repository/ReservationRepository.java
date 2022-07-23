package nosleepcoders.holeinone.shop.repository;

import nosleepcoders.holeinone.shop.domain.Reservation;
import nosleepcoders.holeinone.shop.domain.Store;

import java.util.List;
import java.util.Optional;

/**
 * 예약 리포지토리 인터페이스
 */
public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void delete(Long number);

    Optional<Reservation> findById(Long id);

    Optional<Reservation> findByNumber(String number);

    List<Reservation> findNumberByMemberId(Long id);

    List<Store> findStoreByMemberId(Long id);
}
