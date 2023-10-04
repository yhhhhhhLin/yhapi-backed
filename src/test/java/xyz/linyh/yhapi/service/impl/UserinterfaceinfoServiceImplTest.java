package xyz.linyh.yhapi.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.yhapi.MyApplication;
import xyz.linyh.yhapi.controller.AnalyzeController;
import xyz.linyh.yhapi.controller.UserInterceptorInfoController;
import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.model.vo.InterfaceInfoVO;
import xyz.linyh.yhapi.service.UserinterfaceinfoService;

import java.util.List;

@SpringBootTest(classes = MyApplication.class)
class UserinterfaceinfoServiceImplTest {

    @Autowired
    private UserinterfaceinfoService userinterfaceinfoService;

    @Test
    public void test(){
        AnalyzeController analyzeController = new AnalyzeController();

//        userInterceptorInfoController.
        BaseResponse<List<InterfaceInfoVO>> listBaseResponse = userinterfaceinfoService.analyzeSelfInterfaceInfo(1L);
        System.out.println(listBaseResponse);
    }

}