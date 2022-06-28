package nosleepcoders.holeinone.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 예약 도메인 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Reservation {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reservation_id;

    @Column(nullable = false)
    private String reservation_price;

    @Column(nullable = false)
    private ReservationType status;

    @Column(nullable = false)
    private Long member_id;

    @Column(nullable = false)
    private Long store_id;
}
