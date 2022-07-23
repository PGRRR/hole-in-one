package nosleepcoders.holeinone.shop.controller;

import nosleepcoders.holeinone.annotation.MemberSignInCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shops")
public class ShopController {
    @MemberSignInCheck
    @GetMapping("")
    public String main() {
        return "/shop/main";
    }
}
