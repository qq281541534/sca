package com.theone.contentcenter.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局错误异常处理
 * 注意：这里必须用 @RestControllerAdvice
 *
 * @author liuyu
 */
@RestControllerAdvice
public class GlobalExceptionErrorHandler {

    /**
     * 基于授权未通过的异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e) {

        return new ResponseEntity<>(
                ErrorBody.builder()
                        .body(e.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }
}

/**
 * 错误异常返回消息体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ErrorBody {
    String body;
    int status;
}
