package xyz.linyh.yhapi.service;


import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.model.entity.UserInterfaceinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.linyh.yhapi.ducommon.model.vo.InterfaceInfoVO;

import java.util.List;

/**
* @author lin
* @description 针对表【userinterfaceinfo(用户接口调用次数关系表)】的数据库操作Service
* @createDate 2023-09-11 21:20:10
*/
public interface UserinterfaceinfoService extends IService<UserInterfaceinfo> {

    void validInterfaceInfo(UserInterfaceinfo userInterfaceinfo, boolean add);

    public BaseResponse invokeOk(Long interfaceInfoId, Long userId);

    /**
     * 判断某个用户是否有次数调用某个接口或是否有权限调用某个接口
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    Boolean isInvoke(Long interfaceInfoId, Long userId);

    /**
     * 获取所有接口的调用次数
     * @return
     */
    BaseResponse<List<InterfaceInfoVO>> analyzeInterfaceInfo();
}
