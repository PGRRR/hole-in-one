package nosleepcoders.holeinonejdbc.service;

import nosleepcoders.holeinonejdbc.domain.GolfInfo;
import nosleepcoders.holeinonejdbc.domain.Order;
import nosleepcoders.holeinonejdbc.repository.OrderRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long reservation(Order order) {
        StringBuilder randomNumber;
        do {
            randomNumber = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                randomNumber.append((Math.random() * 9));
            }
        } while (orderRepository.findByNumber(String.valueOf(randomNumber)).isPresent());
        order.setNumber(String.valueOf(randomNumber));
        orderRepository.save(order);
        return order.getId();
    }

    public void cancel(String number) {
        orderRepository.delete(number);
    }

    public List<GolfInfo> findOrderStore(Long id) {
        return orderRepository.findStoreByMemberId(id);
    }

    public List<Order> findOrderNumber(Long id) {
        return orderRepository.findNumberByMemberId(id);
    }
}
