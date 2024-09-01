package com.customerManagementApi.controllers;

import com.customerManagementApi.models.entities.Audit;
import com.customerManagementApi.services.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuditController.RESOURCE_PATH)
public class AuditController {
    static final String RESOURCE_PATH = "/v1/audits";

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping()
    public ResponseEntity<Page<Audit>> getAudits(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
                                                 @RequestParam(value = "sortBy", required = false, defaultValue = "creationDate") String sortBy) {
        return ResponseEntity.ok().body(auditService.getAudits(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,sortBy))));
    }
}
