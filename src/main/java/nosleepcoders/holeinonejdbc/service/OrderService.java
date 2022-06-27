package nosleepcoders.holeinonejdbc.service;

import nosleepcoders.holeinonejdbc.domain.Orders;
import nosleepcoders.holeinonejdbc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long reservation(Long golfInfo_id, Long member_id) {
        Orders orders = new Orders();
        StringBuilder randomNumber;
        do {
            randomNumber = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                randomNumber.append((int)(Math.random() * 9));
            }
        } while (orderRepository.findByNumber(String.valueOf(randomNumber)).isPresent());
        orders.setNumber(String.valueOf(randomNumber));
        orders.setGolfInfo_id(golfInfo_id);
        orders.setMember_id(member_id);
        orderRepository.save(orders);
        System.out.println("예약 번호: " + randomNumber);
        return orders.getId();
    }

    public void cancel(String number) {
        orderRepository.delete(number);
    }

    public List<Orders> findOrderNumber(Long id) {
        return orderRepository.findNumberByMemberId(id);
    }
}
