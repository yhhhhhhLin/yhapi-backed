package xyz.linyh.yhapi.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.linyh.yhapi.ducommon.common.BaseResponse;
import xyz.linyh.yhapi.ducommon.common.ResultUtils;

/**
 * 对sdk进行上传和下载操作
 */
@RestController
@RequestMapping("/sdk")
public class SDKController {

    @PostMapping("/upload")
    public BaseResponse upload(@RequestParam("file") MultipartFile file){
        System.out.println(file);
        return ResultUtils.success(null);

    }

    @GetMapping("/install")
    public BaseResponse install(){
        return ResultUtils.success(null);
    }

}
