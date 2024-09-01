package com.customerManagementApi.services;

import com.customerManagementApi.exceptions.CustomerNotFoundException;
import com.customerManagementApi.models.CustomerRequestDto;
import com.customerManagementApi.models.CustomerResponseDto;
import com.customerManagementApi.models.entities.Customer;
import com.customerManagementApi.repositories.CustomerRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Log log = LogFactory.getLog(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        Customer newCustomer = customerRepository.save(new Customer(customerRequestDto));
        return new CustomerResponseDto(newCustomer);
    }

    public Page<CustomerResponseDto> getCustomers(PageRequest pageRequest){
        Page<Customer> customers = customerRepository.findAll(pageRequest);
        return customers.map(CustomerResponseDto::new);
    }

    @Override
    public CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto customerRequestDto) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer with Id: " + customerId + " not found. Update Operation Failed"));
        updateCustomerDetails(customer,customerRequestDto);
        return new CustomerResponseDto(customerRepository.save(customer));
    }

    private void updateCustomerDetails(Customer customer, CustomerRequestDto customerRequestDto){
        customer.setName(customerRequestDto.getName());
        customer.setAge(customerRequestDto.getAge());
        customer.setDob(LocalDate.parse(customerRequestDto.getDob(), DateTimeFormatter.ofPattern(Customer.datePattern)));
        if(customerRequestDto.getGender()!=null){
            customer.setGender(customerRequestDto.getGender());
        }
        if(customerRequestDto.getAddress()!=null){
            customer.setAddress(customerRequestDto.getAddress());
        }
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer with Id: " + customerId + " not found. Delete Operation Failed"));
        customerRepository.delete(customer);
    }
}
