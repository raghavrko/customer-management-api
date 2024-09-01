package com.customerManagementApi.models.entities.entityListeners;

import com.customerManagementApi.models.enums.ACTIONS;
import com.customerManagementApi.models.enums.STATUS;
import com.customerManagementApi.models.entities.Audit;
import com.customerManagementApi.models.entities.Customer;
import jakarta.persistence.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@EntityListeners(Customer.class)
public class CustomerEntityListener {
    private static final Log log = LogFactory.getLog(CustomerEntityListener.class);

    @Autowired
    private ApplicationContext applicationContext;

    @PostPersist
    public void onPostPersist(Customer customer){
        saveAudit(ACTIONS.CREATED.name(), customer);
    }

    @PreUpdate
    public void onPreUpdate(Customer customer) {
        saveAudit(ACTIONS.UPDATED.name(), customer);
    }

    @PreRemove
    public void onPreRemove(Customer customer) {
        saveAudit(ACTIONS.DELETED.name(), customer);
    }

    private void saveAudit(String action, Customer customer){
        log.debug("Processing action: " + action + " for request: " + customer);
        Audit auditLog = new Audit();
        auditLog.setAction(action);
        auditLog.setCustomerId(customer.getCustomerId());
        auditLog.setRequest(customer.toString());
        auditLog.setStatus(STATUS.SUCCESS.name());
        auditLog.setCreationDate(LocalDateTime.now());
        EntityManager entityManager = applicationContext.getBean(EntityManager.class);
        entityManager.persist(auditLog);
    }

}