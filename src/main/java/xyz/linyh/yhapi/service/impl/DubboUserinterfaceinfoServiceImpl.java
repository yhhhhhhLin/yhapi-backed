package xyz.linyh.yhapi.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.model.entity.UserInterfaceinfo;
import xyz.linyh.yhapi.ducommon.service.DubboUserinterfaceinfoService;
import xyz.linyh.yhapi.service.UserinterfaceinfoService;

@DubboService
public class DubboUserinterfaceinfoServiceImpl implements DubboUserinterfaceinfoService {

    @Autowired
    private UserinterfaceinfoService userinterfaceinfoService;


    @Override
    public BaseResponse invokeOk(Long interfaceInfoId, Long userId) {
        return userinterfaceinfoService.invokeOk(interfaceInfoId,userId);
    }
}
