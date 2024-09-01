package com.customerManagementApi.models;

import com.customerManagementApi.models.validators.ValidDate;
import com.customerManagementApi.models.validators.ValueOfEnum;
import com.customerManagementApi.models.entities.Customer;
import com.customerManagementApi.models.enums.GENDER;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDto {

    Long customerId;

    @NotBlank(message = "Customer Name is mandatory")
    @Size(min = 1,max = 100)
    String name;

    @NotNull(message = "Age is mandatory")
    @Positive(message = "Age must be positive")
    int age;

    @NotNull(message = "Date of Birth is mandatory")
    @ValidDate(past = true)
    String dob;

    @Max(value = 200, message = "Address should not be more than 200 characters long")
    String address;

    @ValueOfEnum(enumClass = GENDER.class)
    String gender;

    public CustomerResponseDto(Customer customer){
        customerId = customer.getCustomerId();
        name = customer.getName();
        age = customer.getAge();
        dob = customer.getDob().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        address = customer.getAddress();
        gender = customer.getGender();
    }
}
