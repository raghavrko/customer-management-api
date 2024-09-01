package com.customerManagementApi.services;

import com.customerManagementApi.exceptions.CustomerNotFoundException;
import com.customerManagementApi.models.CustomerRequestDto;
import com.customerManagementApi.models.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);

    Page<CustomerResponseDto> getCustomers(PageRequest pageRequest);

    CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto customerRequestDto) throws CustomerNotFoundException;

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;
}
