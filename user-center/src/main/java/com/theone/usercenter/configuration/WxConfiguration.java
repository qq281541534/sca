package com.theone.usercenter.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyu
 */
@Configuration
public class WxConfiguration {

    @Bean
    public WxMaConfig wxMaConfig(){
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid("wx09c9069be5f6088a");
        config.setSecret("cd0c06ae3f9e4c138536665f5df43677");
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig wxMaConfig){
        WxMaServiceImpl wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }

}
