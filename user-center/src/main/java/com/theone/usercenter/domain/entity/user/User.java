package com.theone.usercenter.domain.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@ApiModel("用户")
public class User {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 微信ID
     */
    @Column(name = "wx_id")
    @ApiModelProperty("微信ID")
    private String wxId;

    /**
     * 微信昵称
     */
    @Column(name = "wx_nickname")
    @ApiModelProperty("微信昵称")
    private String wxNickname;

    /**
     * 角色
     */
    @ApiModelProperty("角色")
    private String roles;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 创建时间 
     */
    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 积分
     */
    @ApiModelProperty("积分")
    private Integer bonus;

}