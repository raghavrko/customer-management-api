package com.customerManagementApi.controllers;

import com.customerManagementApi.models.CustomerRequestDto;
import com.customerManagementApi.models.CustomerResponseDto;
import com.customerManagementApi.services.CustomerService;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerController.RESOURCE_PATH)
public class CustomerController {
    private static final Log log = LogFactory.getLog(CustomerController.class);

    static final String RESOURCE_PATH = "/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerRequestDto));
    }

    @GetMapping()
    public ResponseEntity<Page<CustomerResponseDto>> getUsers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy) {
        return ResponseEntity.ok().body(customerService.getCustomers(PageRequest.of(page, pageSize, Sort.by(sortBy))));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long customerId,@Valid @RequestBody CustomerRequestDto customerRequestDto){
        return ResponseEntity.ok().body(customerService.updateCustomer(customerId,customerRequestDto));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

}
