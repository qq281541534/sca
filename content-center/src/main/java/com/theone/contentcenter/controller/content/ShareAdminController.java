package com.theone.contentcenter.controller.content;

import com.theone.contentcenter.auth.CheckAuthorization;
import com.theone.contentcenter.domain.dto.content.ShareAuditDTO;
import com.theone.contentcenter.domain.entity.content.Share;
import com.theone.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class ShareAdminController {

    private final ShareService shareService;

    @CheckAuthorization("admin")
    @PutMapping("/audit/{id}")
    public Share auditById(@PathVariable Integer id,@RequestBody ShareAuditDTO auditDTO){
        return shareService.auditById(id, auditDTO);
    }
}
