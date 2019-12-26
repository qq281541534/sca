package com.theone.contentcenter.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 针对MVC的所有接口在发生限流或降级时的异常处理
 *
 * @author liuyu
 */
@Component
public class MyUrlBlockHandler implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        ErrorMsg errorMsg = null;

        if (e instanceof FlowException) {
            errorMsg = ErrorMsg.builder()
                    .status(100)
                    .msg("限流了")
                    .build();
        } if (e instanceof DegradeException) {
            errorMsg = ErrorMsg.builder()
                    .status(101)
                    .msg("降级了")
                    .build();
        } if (e instanceof ParamFlowException) {
            errorMsg = ErrorMsg.builder()
                    .status(102)
                    .msg("热点参数限流")
                    .build();
        } if (e instanceof SystemBlockException) {
            errorMsg = ErrorMsg.builder()
                    .status(103)
                    .msg("系统规则（负载/...不满足要求）")
                    .build();
        } if (e instanceof AuthorityException) {
            errorMsg = ErrorMsg.builder()
                    .status(104)
                    .msg("授权规则不通过")
                    .build();
        }
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(
                httpServletResponse.getWriter(),
                errorMsg
        );
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrorMsg {
    Integer status;
    String msg;
}