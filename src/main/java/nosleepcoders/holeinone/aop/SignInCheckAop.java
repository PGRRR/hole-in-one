package nosleepcoders.holeinone.aop;

import nosleepcoders.holeinone.member.service.MemberService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class SignInCheckAop {

    private final MemberService memberService;

    public SignInCheckAop(MemberService memberService) {
        this.memberService = memberService;
    }

    @Before("@annotation(nosleepcoders.holeinone.annotation.MemberSignInCheck)")
    public void execute() throws Throwable {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        memberService.access((Long) session.getAttribute("member_id"), session);
    }
}

