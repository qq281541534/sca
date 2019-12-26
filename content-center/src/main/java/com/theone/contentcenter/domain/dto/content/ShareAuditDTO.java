package com.theone.contentcenter.domain.dto.content;

import com.theone.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;

/**
 * 审核分享DTO
 * @author liuyu
 */
@Data
public class ShareAuditDTO {

    private AuditStatusEnum auditStatusEnum;

    private String reason;
}
