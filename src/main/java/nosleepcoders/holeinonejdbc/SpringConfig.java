package nosleepcoders.holeinonejdbc;

import nosleepcoders.holeinonejdbc.repository.JdbcMemberRepository;
import nosleepcoders.holeinonejdbc.repository.JdbcOrderRepository;
import nosleepcoders.holeinonejdbc.repository.MemberRepository;
import nosleepcoders.holeinonejdbc.repository.OrderRepository;
import nosleepcoders.holeinonejdbc.service.MemberService;
import nosleepcoders.holeinonejdbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(dataSource);
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository());
    }
    @Bean
    public OrderRepository orderRepository() {
        return new JdbcOrderRepository(dataSource);
    }
}
