package xyz.linyh.yhapi.model.dto.interfaceInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 接口id
     */
    private Long id;


    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * get请求参数
     */
//    todo 修改接收参数
    private List<GRequestParamsDto> getRequestParams;


    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应信息
     */
    private String responseHeader;


    private static final long serialVersionUID = 1L;
}