package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFindTest() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
        prototypeBean1.addCount();;
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);
        prototypeBean2.addCount();;
        assertThat(prototypeBean2 .getCount()).isEqualTo(1);

        applicationContext.close();
    }

    @Test
    void SingletonClientUsePrototype() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientA = applicationContext.getBean(ClientBean.class);
        int countA = clientA.logic();
        assertThat(countA).isEqualTo(1);

        ClientBean clientB = applicationContext.getBean(ClientBean.class);
        int countB = clientB.logic();
        assertThat(countB).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
        private final Provider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean(Provider<PrototypeBean> prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            ++count;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
