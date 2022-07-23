package nosleepcoders.holeinone.shop.controller;

import lombok.RequiredArgsConstructor;
import nosleepcoders.holeinone.annotation.MemberSignInCheck;
import nosleepcoders.holeinone.shop.domain.Reservation;
import nosleepcoders.holeinone.shop.dto.ReservationSaveRequestDto;
import nosleepcoders.holeinone.member.service.MemberService;
import nosleepcoders.holeinone.shop.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class ReservationController {

    private final MemberService memberService;
    private final ReservationService reservationService;

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
