package xyz.linyh.yhapi.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.yapiclientsdk.client.ApiClient;
import xyz.linyh.yhapi.MyApplication;
import xyz.linyh.yhapi.service.InterfaceinfoService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MyApplication.class)
class AnalyzeControllerTest {

    @Autowired
    InterfaceinfoService interfaceinfoService;


    @Test
    void analyzeInterfaceInfo() {
        HashMap<String, Object> reqBody = new HashMap<>();
        reqBody.put("name","tom");
        HttpRequest req = HttpRequest.post("http://localhost:8081/interface/api/p");
        req = req.body(JSONUtil.toJsonStr(reqBody));
        req.execute();
    }

    @Test
    void updateGatewayCache() {
        Boolean aBoolean = interfaceinfoService.updateGatewayCache();
//        HttpResponse execute = HttpRequest.get("http://localhost:8081/yhapi/routes").execute();
    }

    @Autowired
    private ApiClient apiClient;


}