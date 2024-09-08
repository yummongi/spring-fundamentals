package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {

        //private final PrototypeBean prototypeBean; //생성시점에 주입

        private ObjectProvider<PrototypeBean> prototypeBeanProvider; // spring 기능: 권장

        //private Provider<PrototypeBean> prototypeBeanProvider; // JSR-330 Provider: 스프링이 아닌 다른 컨테이너에서도 사용 가능

        /**
         * 	1.	생성자 주입: ClientBean에 ObjectProvider<PrototypeBean>가 주입됩니다.
         * 	이때 ObjectProvider는 단순히 프로토타입 빈을 제공할 수 있는 객체로 주입될 뿐,
         * 	실제로 PrototypeBean의 인스턴스를 미리 생성해서 가지고 있지는 않습니다.
         *
         * 	2.	getObject() 호출: ObjectProvider.getObject()가 호출될 때마다
         * 	스프링 컨테이너에서 새로운 프로토타입 빈을 요청하고 반환합니다.
         * 	따라서, 매번 getObject()를 호출할 때마다 새로운 PrototypeBean 인스턴스가 생성됩니다.
         */
        ClientBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            //prototypeBean.addCount();
            //return prototypeBean.getCount();
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
