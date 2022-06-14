package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.GolfInfo;
import nosleepcoders.holeinonejdbc.domain.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;

/**
 * 예약 리포지토리 인터페이스
 */
public interface OrderRepository {

    Order save(Order order);

    String delete(String number);

    Optional<Order> findById(Long id);

    Optional<Order> findByNumber(String number);

    List<Order> findNumberByMemberId(Long id);

    List<GolfInfo> findStoreByMemberId(Long id);
}
