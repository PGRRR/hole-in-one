package nosleepcoders.holeinonejdbc.controller;

import nosleepcoders.holeinonejdbc.domain.Orders;
import nosleepcoders.holeinonejdbc.service.MemberService;
import nosleepcoders.holeinonejdbc.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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
            memberService.access(id, session);
            List<Orders> ordersNumbers = orderService.findOrderNumber(id);
            model.addAttribute("orderNumbers", ordersNumbers);
            return "/order/list";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/store")
    public String reg(@PathVariable Long id, String golfInfo_id,HttpSession session) {
        memberService.access(id, session);
        orderService.reservation(Long.parseLong(golfInfo_id), id);
        return "redirect:/orders/{id}";
    }

    @GetMapping("/{id}/{number}")
    public String cancel(@PathVariable String number) {
        orderService.cancel(number);
        return "redirect:/orders/{id}";
    }
}
