package xyz.linyh.yhapi.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.model.entity.Interfaceinfo;
import xyz.linyh.yhapi.ducommon.model.entity.UserInterfaceinfo;
import xyz.linyh.yhapi.ducommon.model.vo.InterfaceInfoVO;
import xyz.linyh.yhapi.mapper.UserinterfaceinfoMapper;
import xyz.linyh.yhapi.service.InterfaceinfoService;
import xyz.linyh.yhapi.service.UserinterfaceinfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lin
 */
@RestController
@RequestMapping("/analyze")
public class AnalyzeController {

    @Resource
    private UserinterfaceinfoService userinterfaceinfoService;



    @RequestMapping
    public BaseResponse<List<InterfaceInfoVO>> analyzeInterfaceInfo(){
        return userinterfaceinfoService.analyzeInterfaceInfo();

    }
}
