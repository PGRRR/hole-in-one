package nosleepcoders.holeinonejdbc;

import nosleepcoders.holeinonejdbc.domain.Article;
import nosleepcoders.holeinonejdbc.repository.*;
import nosleepcoders.holeinonejdbc.service.ArticleService;
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

    @Bean
    public ArticleService articleService() { return new ArticleService(articleRepository());}

    @Bean
    public ArticleRepository articleRepository(){ return new JdbcArticleRepository(dataSource);}
}
