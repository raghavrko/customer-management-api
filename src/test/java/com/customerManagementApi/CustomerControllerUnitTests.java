package com.customerManagementApi;

import com.customerManagementApi.controllers.CustomerController;
import com.customerManagementApi.exceptions.CustomerNotFoundException;
import com.customerManagementApi.models.CustomerRequestDto;
import com.customerManagementApi.models.CustomerResponseDto;
import com.customerManagementApi.services.CustomerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerUnitTests {

    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;


    @Autowired
    MockMvc mvc;

    @Test
    public void testCreateCustomer_Success() {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("John Doe",23,"1998-01-20","M-2, test address","M");

        CustomerResponseDto expectedResponse = new CustomerResponseDto(1L,"John Doe",23,"1998-01-20","M-2, test address","M");
        when(customerService.createCustomer(customerRequestDto)).thenReturn(expectedResponse);

        ResponseEntity<CustomerResponseDto> response = customerController.createCustomer(customerRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test()
    public void testCreateCustomer_InvalidRequest() throws Exception {
//        assertThrows(MethodArgumentNotValidException.class,()-> customerController.createCustomer(new CustomerRequestDto(null,-99,"20-01-1999","M-2, test address","M")));
    String customer = "{\n" +
            "    \"age\":-24,\n" +
            "    \"dob\":\"2000-31-01\",\n" +
            "    \"gender\":\"V\"\n" +
            "}";
    mvc.perform(MockMvcRequestBuilders.post("/v1/customers")
            .content(customer)
            .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization","Basic dXNlcjp0ZXN0MTIz"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetUsers_Success() {
        CustomerResponseDto expectedResponse = new CustomerResponseDto(1L,"John Doe",23,"1998-01-20","M-2, test address","M");
        List<CustomerResponseDto> list = new ArrayList<>();
        list.add(expectedResponse);
        Page<CustomerResponseDto> expectedPage = new PageImpl<>(list);
        when(customerService.getCustomers(PageRequest.of(0,10, Sort.by("name")))).thenReturn(expectedPage);

        ResponseEntity<Page<CustomerResponseDto>> response = customerController.getUsers(0, 10, "name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPage, response.getBody());
    }


    @Test
    public void testUpdateCustomer_Success() {
        Long customerId = 1L;
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("John Doe",25,"1999-01-20","M-2, test address","M");
        CustomerResponseDto expectedResponse = new CustomerResponseDto(1L,"John Doe",25,"1999-01-20","M-2, test address","M");
        when(customerService.updateCustomer(customerId, customerRequestDto)).thenReturn(expectedResponse);

        ResponseEntity<CustomerResponseDto> response = customerController.updateCustomer(customerId, customerRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test()
    public void testUpdateCustomer_InvalidRequest() throws Exception {
        long customerId = 1L;
//        assertThrows(MethodArgumentNotValidException.class,()-> customerController.updateCustomer(customerId,customerRequestDto));
        String customer = "{\n" +
                "    \"age\":-24,\n" +
                "    \"dob\":\"2000-31-01\",\n" +
                "    \"gender\":\"V\"\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.put("/v1/customers/"+ customerId)
                .content(customer)
                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization","Basic dXNlcjp0ZXN0MTIz"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeleteCustomer_Success() {
        Long customerId = 1L;
        doNothing().when(customerService).deleteCustomer(customerId);
        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomer_UserNotFound() {
        Long customerId = 1L;
        doThrow(new CustomerNotFoundException("")).doNothing().when(customerService).deleteCustomer(customerId);
        assertThrows(CustomerNotFoundException.class, ()-> customerController.deleteCustomer(customerId));
    }
}