package nosleepcoders.holeinone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 예약 엔티티 객체
 */
@Getter
@NoArgsConstructor
@Entity(name = "reservations")
public class Reservation {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reservation_id;

    @Column(nullable = false)
    private Long reservation_price;

    @Column(nullable = false)
    private ReservationType status;

    @Column(nullable = false)
    private Long member_id;

    @Column(nullable = false)
    private Long store_id;

    @Builder
    public Reservation(Long reservation_id, Long reservation_price, ReservationType status, Long member_id, Long store_id) {
        this.reservation_id = reservation_id;
        this.reservation_price = reservation_price;
        this.status = status;
        this.member_id = member_id;
        this.store_id = store_id;
    }
}
