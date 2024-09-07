package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //basePackages = "hello.core.member", //지정 위치 패키지 포함 하위 패키지
        //basePackageClasses = AutoAppConfig.class, //해당 위치의 패키지 기준, 하위 패키지 : default 설정 (생략 권장)
        excludeFilters= @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
