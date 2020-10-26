package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @ClassName CustomExceptionHandler
 * @Descrintion 自定义异常类（捕获处理异常）  助手类
 * @Author bd
 * @Date 2020/6/7 17:27
 * @Version 1.0
 **/
@RestControllerAdvice
public class CustomExceptionHandler {

    //处理异常  MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException exception) {

        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500K");
    }
}
