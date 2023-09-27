//package xyz.linyh.yhapigateway;
//
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.apache.dubbo.config.annotation.DubboService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import xyz.linyh.yhapigateway.dubbo.DemoService;
//import xyz.linyh.yhapi.ducommon.service.UserinterfaceinfoService;
//
//@SpringBootTest(classes = YhapiGatewayApplication.class)
//class YhapiGatewayApplicationTests {
//
//    @DubboReference
//    private DemoService demoService;
//
//    @DubboReference
//    private UserinterfaceinfoService userinterfaceinfoService;
//
//    @Test
//    void contextLoads() {
////        System.out.println(demoService.sayHello("tom"));
//        System.out.println(userinterfaceinfoService.invokeOk(1L, 1L));
//        System.getProperty("java.classpath");
//    }
//
//}
