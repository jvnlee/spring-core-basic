package spring.springcorebasic.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.springcorebasic.common.MyLogger;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        /*
        MyLogger Bean은 request scope라서 클라이언트로부터 HTTP request가 왔을 때 비로소 생성이 되기 때문에 처음 스프링 컨테이너를 띄웠을 때는 존재할 수가 없다.
        따라서 MyLogger를 직접 의존성으로 주입받으면 처음 스프링 컨테이너를 띄울 때(요청이 없는 상태), 아직 없는 Bean을 요청하는 꼴이므로 에러가 발생한다.

        이런 문제를 막기 위해 ObjectProvider를 사용해서 MyLogger Bean을 나중에 받아올 수 있도록 한다.
        ObjectProvider에게 MyLogger Bean을 구해달라는 명령을, HTTP 요청이 들어왔을 때 호출되는 메서드 logDemo()의 로직 안에 넣어 해결할 수 있다.
        */
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
