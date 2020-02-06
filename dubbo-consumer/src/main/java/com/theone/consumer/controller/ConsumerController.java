package com.theone.consumer.controller;

import com.theone.apimodel.dto.UserDTO;
import com.theone.apimodel.rpc.UserServiceApi;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Reference
    private UserServiceApi userServiceApi;

    @GetMapping("findById/{id}")
    public UserDTO findById(@PathVariable Integer id){
        return userServiceApi.findById(id);
    }
}
