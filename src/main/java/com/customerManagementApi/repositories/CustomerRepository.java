package com.customerManagementApi.repositories;

import com.customerManagementApi.models.entities.Customer;
import jakarta.persistence.PrePersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
