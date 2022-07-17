package nosleepcoders.holeinone.controller;

import nosleepcoders.holeinone.annotation.MemberSignInCheck;
import nosleepcoders.holeinone.domain.Reservation;
import nosleepcoders.holeinone.dto.ReservationSaveRequestDto;
import nosleepcoders.holeinone.service.MemberService;
import nosleepcoders.holeinone.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class ReservationController {

    private final MemberService memberService;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService, MemberService memberService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
    }
    @MemberSignInCheck
    @GetMapping("/{id}")
    public String list(@PathVariable(value = "id") Long member_id, HttpSession session, Model model) {
        try {
            memberService.access(member_id, session);
            List<Reservation> reservationNumbers = reservationService.findOrderNumber(member_id);
            model.addAttribute("orderNumbers", reservationNumbers);
            return "/order/list";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    @MemberSignInCheck
    @GetMapping("/{id}/store")
    public String reservation(@PathVariable(value = "id") Long member_id, ReservationSaveRequestDto requestDto, HttpSession session) {
        memberService.access(member_id, session);
        reservationService.reservation(requestDto);
        return "redirect:/orders/{id}";
    }
    @MemberSignInCheck
    @GetMapping("/{id}/{store_id}")
    public String cancel(@PathVariable Long store_id) {
        reservationService.cancel(store_id);
        return "redirect:/orders/{id}";
    }
}
