package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        //NetworkClientV0 client = ac.getBean(NetworkClientV0.class);
        //NetworkClientV1 client = ac.getBean(NetworkClientV1.class);
        NetworkClientV2 client = ac.getBean(NetworkClientV2.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        /**
         * 1. 스프링 컨테이너 생성
         * 2. 빈 생성
         * 3. 빈 생성 후 의존 관계 주입
         * 4. @PostConstruct: 의존관계 주입 후 콜백 메소드 실행
         * 5. @PreDestroy: 스프링 컨테이너 소멸 직전 콜백 메소드 실행
         */

        /**
         * 스프링이 빈을 생성하기 위해 @Bean이 있는 메서드를 호출하게 됩니다.
         * 이 과정에서 new NetworkClient(); 를 통해서 해당 객체가 생성되고,
         * 그 다음에 setUrl이 바로 호출되면서 URL 정보도 입력됩니다.
         * 여기까지 모두 빈 생성 과정에서 일어나는 일입니다.
         * networkClient 객체가 @Bean 메서드에서 반환되면 이후에 스프링 빈으로 등록되게 됩니다.
         */

        /** 인터페이스 구현을 활용한 빈 콜백 */
/*        @Bean
        public NetworkClientV0 networkClient0() {
            NetworkClientV0 networkClient = new NetworkClientV0();
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }*/

        /**
         * Bean 속성을 활용한 빈 콜백
         * @Bean(initMethod = "init") // destroyMethod = (inferred) default : 추론하여 close()나 shutdown()메소드 호출
         * 추론 기능을 사용하지 않으려면 destroyMethod = "" 처럼 빈 문자열으로 설정하면 된다.
        */
/*        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClientV1 networkClient1() {
            NetworkClientV1 networkClient = new NetworkClientV1();
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }*/

        /**
         * 어노테이션을 활용한 빈 콜개 @PostConstruct, @PreDestroy
         * 권장 방법!
         */
        @Bean
        public NetworkClientV2 networkClient2() {
            NetworkClientV2 networkClient = new NetworkClientV2();
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }
    }
}

