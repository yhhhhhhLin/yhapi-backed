package xyz.linyh.yhapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import xyz.linyh.yhapi.annotation.AuthCheck;
import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.common.DeleteRequest;
import xyz.linyh.yhapi.ducommon.common.ErrorCode;
import xyz.linyh.yhapi.ducommon.common.ResultUtils;
import xyz.linyh.yhapi.ducommon.constant.CommonConstant;
import xyz.linyh.yhapi.ducommon.model.entity.Interfaceinfo;
import xyz.linyh.yhapi.ducommon.model.entity.User;
import xyz.linyh.yhapi.ducommon.model.entity.UserInterfaceinfo;
import xyz.linyh.yhapi.exception.BusinessException;
import xyz.linyh.yhapi.model.dto.interfaceInfo.*;
import xyz.linyh.yhapi.service.InterfaceinfoService;
import xyz.linyh.yhapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import xyz.linyh.yhapi.service.UserinterfaceinfoService;
import xyz.linyh.yhapiinterfaceclientsdk.client.TestClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 对接口的增删改查
 *
 *
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterceptorInfoController {

    @Resource
    private InterfaceinfoService interfaceinfoService;

    @Resource
    private UserinterfaceinfoService userinterfaceinfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceInfo = new Interfaceinfo();
        interfaceInfo.setStatus(0);
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验参数是否正确
        interfaceinfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceinfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Interfaceinfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceinfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceInfo = new Interfaceinfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceinfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        Interfaceinfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceinfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

//    接口上线
    @PostMapping("/online")
    public BaseResponse online(@RequestBody IdRequest idRequest,
                          HttpServletRequest request){
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = idRequest.getId();
        // 判断是否存在
        Interfaceinfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        LambdaUpdateWrapper<Interfaceinfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Interfaceinfo::getId,id)
                .set(Interfaceinfo::getStatus,1);
        boolean result = interfaceinfoService.update(wrapper);
        return ResultUtils.success(result);
    }

//    接口下线
    @PostMapping("/offline")
    public BaseResponse offline(@RequestBody IdRequest idRequest,
                          HttpServletRequest request){
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = idRequest.getId();
        // 判断是否存在
        Interfaceinfo oldInterfaceInfo = interfaceinfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        LambdaUpdateWrapper<Interfaceinfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Interfaceinfo::getId,id)
                .set(Interfaceinfo::getStatus,0);
        boolean result = interfaceinfoService.update(wrapper);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Interfaceinfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceInfo = interfaceinfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

//    先全部用管理员ak和sk发送，后面改为根据每一个用户发送


//    todo 只是简单的，需要改为根据每一个请求获取请求参数，然后传递
    @PostMapping("/invoke")
    public String invokeInterfaceById(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                      HttpServletRequest request){
//        判断参数是否有效
        if(interfaceInfoInvokeRequest==null ||interfaceInfoInvokeRequest.getId()==null ||interfaceInfoInvokeRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        int i = 10/0;
//        判断接口是否有效
        Interfaceinfo interfaceInfo = interfaceinfoService.getById(interfaceInfoInvokeRequest.getId());
        if(interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        User user = userService.getLoginUser(request);

//        判断是否还有调用次数
        Boolean isInvoke = userinterfaceinfoService.isInvoke(interfaceInfoInvokeRequest.getId(), user.getId());
        if(!isInvoke){
            throw new BusinessException(ErrorCode.NOT_INVOKE_NUM_ERROR);
        }

//        获取用户的ak和sk
        String accessKey = user.getAccessKey();
        String secretKey = user.getSecretKey();


        Gson gson = new Gson();
        Map<String, Object> testUser = gson.fromJson(interfaceInfoInvokeRequest.getRequestParams(), Map.class);

//      todo  如果请求体为空，也需要判断


//        通过sdk发送请求 todo 现在只是固定的
//        直接发送到网关,网关判断要转发要哪个地址
        TestClient testClient = new TestClient(accessKey, secretKey);
        String resp = testClient.testGet((String) testUser.get("name"));

        return resp;
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<Page<Interfaceinfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        Interfaceinfo interfaceInfoQuery = new Interfaceinfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<Interfaceinfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        Page<Interfaceinfo> page = new Page<>(interfaceInfoQueryRequest.getCurrent(),interfaceInfoQueryRequest.getPageSize());
        Page<Interfaceinfo> interfaceInfoList = interfaceinfoService.page(page, queryWrapper);
//        List<Interfaceinfo> interfaceInfoList = interfaceinfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<Interfaceinfo>> listPnterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceInfoQuery = new Interfaceinfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
//        String content = interfaceInfoQuery.getContent();
        // content 需支持模糊搜索
//        interfaceInfoQuery.setContent(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Interfaceinfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<Interfaceinfo> interfaceInfoPage = interfaceinfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    // endregion

}
