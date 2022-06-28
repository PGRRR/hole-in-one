package nosleepcoders.holeinone.service;

import nosleepcoders.holeinone.domain.Reservation;
import nosleepcoders.holeinone.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long reservation(Long store_id, Long member_id) {
        Reservation reservation = new Reservation();
        StringBuilder randomNumber;
        do {
            randomNumber = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                randomNumber.append((int)(Math.random() * 9));
            }
        } while (reservationRepository.findByNumber(String.valueOf(randomNumber)).isPresent());
        reservation.setReservation_id(Long.valueOf(String.valueOf(randomNumber)));
        reservation.setStore_id(store_id);
        reservation.setMember_id(member_id);
        reservationRepository.save(reservation);
        System.out.println("예약 번호: " + randomNumber);
        return reservation.getReservation_id();
    }

    public void cancel(String number) {
        reservationRepository.delete(number);
    }

    public List<Reservation> findOrderNumber(Long id) {
        return reservationRepository.findNumberByMemberId(id);
    }
}
