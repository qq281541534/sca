package com.theone.common.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespBody<T> {

    private Integer status;

    private String msg;

    private T data;
}
