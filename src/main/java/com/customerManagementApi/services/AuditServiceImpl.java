package com.customerManagementApi.services;

import com.customerManagementApi.models.entities.Audit;
import com.customerManagementApi.repositories.AuditRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService{
    private static final Log log = LogFactory.getLog(AuditServiceImpl.class);

    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void saveAudit(Audit auditLog) {
        auditRepository.save(auditLog);
    }

    @Override
    public Page<Audit> getAudits(PageRequest pageRequest) {
        return auditRepository.findAll(pageRequest);
    }
}
