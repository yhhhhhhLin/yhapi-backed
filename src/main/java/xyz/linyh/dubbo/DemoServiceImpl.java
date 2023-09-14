package xyz.linyh.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("调用到了-------------------");
        return "Hello " + name;
    }
}
