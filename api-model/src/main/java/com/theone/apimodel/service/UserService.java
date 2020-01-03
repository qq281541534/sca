package com.theone.apimodel.service;

import com.theone.apimodel.dto.UserDTO;

public interface UserService {

    UserDTO findById(Integer id);
}
