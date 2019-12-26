package com.theone.contentcenter.domain.dto.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户添加积分的消息体
 * @author liuyu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddBonusMsgDTO {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 积分值
     */
    private Integer bonus;
    /**
     * 描述
     */
    private String description;
    /**
     * 事件
     */
    private String event;
}


