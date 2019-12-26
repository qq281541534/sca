package com.theone.contentcenter.domain.entity.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author liuyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "mid_user_share")
public class MidUserShare {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "share_id")
    private Integer shareId;

}