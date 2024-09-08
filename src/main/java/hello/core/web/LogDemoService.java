package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    //프록시 적용 전
    //private final ObjectProvider<MyLogger> myLoggerProvider;

    //프록시 적용 후 (proxyMode = ScopedProxyMode.TARGET_CLASS)
    private final MyLogger myLogger;
    public void logic(String id) {
        myLogger.log("service id = " + id);
    }
}
