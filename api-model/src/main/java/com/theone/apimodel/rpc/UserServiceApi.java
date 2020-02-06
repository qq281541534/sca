package com.theone.apimodel.rpc;

import com.theone.apimodel.dto.UserDTO;

/**
 * 基于RPC
 * @author liuyu
 */
public interface UserServiceApi {

    /**
     * 通过ID查询用户
     *
     * @param id
     * @return
     */
    UserDTO findById(Integer id);
}
