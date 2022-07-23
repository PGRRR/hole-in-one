package nosleepcoders.holeinone.shop.service;

import lombok.RequiredArgsConstructor;
import nosleepcoders.holeinone.shop.domain.Reservation;
import nosleepcoders.holeinone.shop.dto.ReservationSaveRequestDto;
import nosleepcoders.holeinone.shop.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public Long reservation(ReservationSaveRequestDto requestDto) {
        return reservationRepository.save(requestDto.toEntity()).getReservation_id();
    }
    @Transactional
    public void cancel(Long number) {
        reservationRepository.delete(number);
    }
    @Transactional
    public List<Reservation> findOrderNumber(Long id) {
        return reservationRepository.findNumberByMemberId(id);
    }
}
