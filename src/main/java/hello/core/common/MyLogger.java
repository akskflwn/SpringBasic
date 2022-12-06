package hello.core.common;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * ScopedProxyMode
 * - 적용대상이 인터페이스가 아닌 클래스면 TARGET_CLASS 를 선택
 * - 적용대상이 인터페이스라면 INTERFACES 를 선택
 * 이렇게 하면 MyLogger 의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관없이
 * 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.
 */
@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuId;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("[" + uuId + "]" +"[" + requestURL+"] " + message);
    }

    @PostConstruct
    public void init(){
        uuId= UUID.randomUUID().toString();
        System.out.println("[" + uuId + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuId + "] request scope bean close : " + this);
    }
}
