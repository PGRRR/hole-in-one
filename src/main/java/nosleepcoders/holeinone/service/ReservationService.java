package nosleepcoders.holeinone.service;

import lombok.RequiredArgsConstructor;
import nosleepcoders.holeinone.domain.Reservation;
import nosleepcoders.holeinone.dto.ReservationSaveRequestDto;
import nosleepcoders.holeinone.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
