package com.example.myapp.common.exception;

import com.example.myapp.common.http.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public CommonResponse handleBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        log.error(e.getMessage(), e);
        return CommonResponse.error(message);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public CommonResponse handleServiceException(ServiceException e, HttpServletRequest request) {
        Integer code = e.getCode();
        log.error(e.getMessage(), e);
        return (code != null) ? CommonResponse.error(code, e.getMessage()) : CommonResponse.error(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常，一般是代码问题
     */
    @ExceptionHandler(RuntimeException.class)
    public CommonResponse handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生运行时异常.", requestURI, e);
        return CommonResponse.error("Backend RuntimeException");
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return CommonResponse.error("Backend Exception");
    }
}
