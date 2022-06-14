package nosleepcoders.holeinonejdbc.controller;

import nosleepcoders.holeinonejdbc.domain.GolfInfo;
import nosleepcoders.holeinonejdbc.domain.Member;
import nosleepcoders.holeinonejdbc.domain.Order;
import nosleepcoders.holeinonejdbc.service.MemberService;
import nosleepcoders.holeinonejdbc.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;

    public OrderController(OrderService orderService, MemberService memberService) {
        this.orderService = orderService;
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public String list(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Optional<Member> member = memberService.access(id, session);
            List<GolfInfo> stores = orderService.findOrderStore(id);
            List<Order> orderNumbers = orderService.findOrderNumber(id);
            model.addAttribute("stores", stores);
            model.addAttribute("orderNumbers", orderNumbers);
            return "/order/list";
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/{number}")
    public String cancel(@PathVariable String number) {
        orderService.cancel(number);
        return "/order/list";
    }
}
