package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.Orders;
import nosleepcoders.holeinonejdbc.domain.Store;

import java.util.List;
import java.util.Optional;

/**
 * 예약 리포지토리 인터페이스
 */
public interface OrderRepository {

    Orders save(Orders orders);

    String delete(String number);

    Optional<Orders> findById(Long id);

    Optional<Orders> findByNumber(String number);

    List<Orders> findNumberByMemberId(Long id);

    List<Store> findStoreByMemberId(Long id);
}
