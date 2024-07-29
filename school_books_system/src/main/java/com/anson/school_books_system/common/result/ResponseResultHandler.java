package com.anson.school_books_system.common.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Anson（about：）
 * @description
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    /**
     *  判斷是否處理結果集
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        Method method = methodParameter.getMethod();
        Class clazz = Objects.requireNonNull(method, "method is null").getDeclaringClass();

        // 只處理 ResponseResult 標註的類或方法
        ResponseResult annotation = (ResponseResult) clazz.getAnnotation(ResponseResult.class);
        if (Objects.isNull(annotation)) {
            annotation = method.getAnnotation(ResponseResult.class);
        }

        // 如果是FileSystemResource 則不攔截
        if (method.getAnnotatedReturnType().getType().getTypeName()
                .equals(FileSystemResource.class.getTypeName())) {
            return false;
        }
        return annotation != null && !annotation.ignore();
    }

    /**
     * 如果需要處理結果集，會來到這個方法處理結果集
     *
     * @return
     */
     @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object data,
                                  MethodParameter mediaType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse serverHttpResponse) {
        SuccessInfo successInfo = new SuccessInfo(data);

        // 處理 String 類型情况
        if ((data instanceof String) && MediaType.TEXT_PLAIN_VALUE.equals(selectedContentType.toString())) {
            ObjectMapper om = new ObjectMapper();
            serverHttpResponse.getHeaders().set("Content-Type", "application/json");
            return om.writeValueAsString(successInfo);
        }

        // 處理 空（null） 類型情况
        if (Objects.isNull(data) && MediaType.TEXT_PLAIN_VALUE.equals(selectedContentType.toString())) {
            ObjectMapper om = new ObjectMapper();
            serverHttpResponse.getHeaders().set("Content-Type", "application/json");
            return om.writeValueAsString(successInfo);
        }

        return successInfo;
    }
}
