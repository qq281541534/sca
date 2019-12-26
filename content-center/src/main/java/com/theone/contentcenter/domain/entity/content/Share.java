package com.theone.contentcenter.domain.entity.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liuyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "share")
public class Share {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否原创
     */
    @Column(name = "is_original")
    private Boolean isOriginal;

    private String autor;

    private String cover;

    private String summary;

    private Integer price;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "show_flag")
    private Boolean showFlag;

    @Column(name = "audit_status")
    private String auditStatus;

    private String reason;

}