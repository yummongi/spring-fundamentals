package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClientV1 {
    private String url;

    public NetworkClientV1() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message: " + message);
    }

    //서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    // 의존관계 주입이 끝나면 call
    public void init() {
        System.out.println("NetworkClientV1.init");
        connect();
        call("초기화 연결 메시지");
    }

    // 스프링 컨테이너가 종료되기 직전에 call
    public void close() {
        System.out.println("NetworkClientV1.close");
        disconnect();
    }
}
