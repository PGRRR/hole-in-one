package nosleepcoders.holeinone.controller;

import nosleepcoders.holeinone.domain.Reservation;
import nosleepcoders.holeinone.service.MemberService;
import nosleepcoders.holeinone.service.ReservationService;
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

    public ReservationController(ReservationService reservationService, MemberService memberService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public String list(@PathVariable Long id, HttpSession session, Model model) {
        try {
            memberService.access(id, session);
            List<Reservation> reservationNumbers = reservationService.findOrderNumber(id);
            model.addAttribute("orderNumbers", reservationNumbers);
            return "/order/list";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/store")
    public String reg(@PathVariable Long id, String golfInfo_id,HttpSession session) {
        memberService.access(id, session);
        reservationService.reservation(Long.parseLong(golfInfo_id), id);
        return "redirect:/orders/{id}";
    }

    @GetMapping("/{id}/{number}")
    public String cancel(@PathVariable String number) {
        reservationService.cancel(number);
        return "redirect:/orders/{id}";
    }
}
