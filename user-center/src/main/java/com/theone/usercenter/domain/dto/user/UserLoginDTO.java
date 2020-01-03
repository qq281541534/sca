package com.theone.usercenter.domain.dto.user;

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
public class UserLoginDTO {

    /**
     * code
     */
    private String code;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 昵称
     */
    private String wxNickName;
}
