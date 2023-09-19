package xyz.linyh.yhapi.service;

import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.model.entity.Interfaceinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lin
* @description 针对表【interfaceinfo(接口信息表)】的数据库操作Service
* @createDate 2023-09-03 19:31:19
*/
public interface InterfaceinfoService extends IService<Interfaceinfo> {

    /**
     * 对接口信息进行校验
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(Interfaceinfo interfaceInfo, boolean add);

    Interfaceinfo getInterfaceInfoByURL(String interfaceURL,String method);
}
