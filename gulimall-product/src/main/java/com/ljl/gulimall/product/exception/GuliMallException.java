package com.ljl.gulimall.product.exception;

import com.ljl.common.exception.BizCodeEnume;
import com.ljl.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 集中处理所有异常处理
 * 1.添加@RestControllerAdvice注解
 */
/**
 * 使用JSR303校验，1、在实体类的字段上加上校验注解 列如@NotBlank，可以自定义返回信息
 * 2、加上@Valid注解开启校验
 * 3、BindResult可以获取校验结果
 */
@Slf4j
@RestControllerAdvice(basePackages = "com/ljl/gulimall/product/controller")
public class GuliMallException {

    /**
     * 校验异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型{}", e.getMessage(), e.getClass());
        BindingResult result = e.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach((item) -> {
            errorMap.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(), BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {

        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(), BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }

}
