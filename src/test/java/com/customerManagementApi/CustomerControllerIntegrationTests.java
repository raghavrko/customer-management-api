package com.customerManagementApi;

import com.customerManagementApi.controllers.CustomerController;
import com.customerManagementApi.exceptions.CustomerNotFoundException;
import com.customerManagementApi.models.CustomerRequestDto;
import com.customerManagementApi.models.CustomerResponseDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerIntegrationTests {

    @Autowired
    private CustomerController customerController;

    @Test
    @Order(1)
    public void testCreateCustomer_Success() {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("John Doe",23,"1998-01-20","M-2, test address","M");

        CustomerResponseDto expectedResponse = new CustomerResponseDto(1L,"John Doe",23,"1998-01-20","M-2, test address","M");

        ResponseEntity<CustomerResponseDto> response = customerController.createCustomer(customerRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }


    @Test
    @Order(2)
    public void testGetUsers_Success() {
        CustomerResponseDto expectedResponse = new CustomerResponseDto(1L,"John Doe",23,"1998-01-20","M-2, test address","M");
        List<CustomerResponseDto> list = new ArrayList<>();
        list.add(expectedResponse);
        Page<CustomerResponseDto> expectedPage = new PageImpl<>(list,PageRequest.of(0,10,Sort.by("name")),1L);

        ResponseEntity<Page<CustomerResponseDto>> response = customerController.getUsers(0, 10, "name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPage, response.getBody());
    }


    @Test
    @Order(3)
    public void testUpdateCustomer_Success() {
        Long customerId = 1L;
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("John Doe",25,"1999-01-20","M-2, test address","M");
        CustomerResponseDto expectedResponse = new CustomerResponseDto(1L,"John Doe",25,"1999-01-20","M-2, test address","M");

        ResponseEntity<CustomerResponseDto> response = customerController.updateCustomer(customerId, customerRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @Order(4)
    public void testDeleteCustomer_Success() {
        Long customerId = 1L;
        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testDeleteCustomer_UserNotFound() {
        Long customerId = 1L;
        assertThrows(CustomerNotFoundException.class,()-> customerController.deleteCustomer(customerId));
    }

    @Test
    @Order(6)
    public void testUpdateCustomer_UserNotFound() {
        Long customerId = 1L;
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("John Doe",25,"1999-01-20","M-2, test address","M");
        assertThrows(CustomerNotFoundException.class,()-> customerController.updateCustomer(customerId,customerRequestDto));
    }
}