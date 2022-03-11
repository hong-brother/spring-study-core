package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycle {

    @Test
    void beanLifeCycleTest() {
        ConfigurableApplicationContext  applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = applicationContext.getBean(NetworkClient.class);
        applicationContext.close();

    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient () {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://localhost:8080");
            return networkClient;
        }
    }
}
