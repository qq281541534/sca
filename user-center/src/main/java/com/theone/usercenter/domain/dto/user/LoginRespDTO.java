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
public class LoginRespDTO {

    private JwtTokenRespDTO token;

    private UserRespDTO user;
}
