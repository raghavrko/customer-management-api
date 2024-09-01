package com.customerManagementApi.services;

import com.customerManagementApi.models.entities.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AuditService {

    void saveAudit(Audit auditLog);

    Page<Audit> getAudits(PageRequest pageRequest);
}
