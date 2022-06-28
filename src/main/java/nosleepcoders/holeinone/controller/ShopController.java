package nosleepcoders.holeinone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shops")
public class ShopController {
    @GetMapping("")
    public String main() {
        return "/shop/main";
    }
}
