package com.customerManagementApi.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long auditId;

    @NotNull
    String action;

    @NotNull
    Long customerId;

    String request;

    String status;

    @NotNull
    @Column(name = "creationDate")
    LocalDateTime creationDate;
}
