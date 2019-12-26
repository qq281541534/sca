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
public class JwtTokenRespDTO {

    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Long expirationTime;
}
