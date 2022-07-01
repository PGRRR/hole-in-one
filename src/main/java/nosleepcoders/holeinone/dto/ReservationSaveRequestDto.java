package nosleepcoders.holeinone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nosleepcoders.holeinone.domain.Reservation;

@Getter
@NoArgsConstructor
public class ReservationSaveRequestDto {
    private Long reservation_price;
    private Long member_id;
    private Long store_id;

    @Builder
    public ReservationSaveRequestDto(Long reservation_price, Long member_id, Long store_id) {
        this.reservation_price = reservation_price;
        this.member_id = member_id;
        this.store_id = store_id;
    }

    // request DTO 로 받은 Reservation 객체를 Entity 화하여 저장하는 용도
    public Reservation toEntity() {
        return Reservation.builder()
                .reservation_price(reservation_price)
                .member_id(member_id)
                .store_id(store_id)
                .build();
    }
}
