package com.jj.servicebase.exceptionhandler;



import com.jj.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 统一全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    // 特殊异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException arithmeticException) {
        arithmeticException.printStackTrace();
        return R.error().message("执行了特殊异常处理");
    }


    // 自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException guliException) {
        log.error(guliException.getMsg());
        guliException.printStackTrace();
        return R.error().code(guliException.getCode()).message(guliException.getMsg());
    }

}
