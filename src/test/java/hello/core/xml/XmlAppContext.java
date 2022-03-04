package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlAppContext {

    @Test
    @DisplayName("bean 설정을 xml로 한다.")
    void xmlAppContext() {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
         assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
