package xyz.linyh.yhapi.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.linyh.yapiclientsdk.client.ApiClient;
import xyz.linyh.yhapi.MyApplication;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MyApplication.class)
class AnalyzeControllerTest {


    @Test
    void analyzeInterfaceInfo() {
        HashMap<String, Object> reqBody = new HashMap<>();
        reqBody.put("name","tom");
        HttpRequest req = HttpRequest.post("http://localhost:8081/interface/api/p");
        req = req.body(JSONUtil.toJsonStr(reqBody));
        req.execute();
    }

    @Autowired
    private ApiClient apiClient;


}