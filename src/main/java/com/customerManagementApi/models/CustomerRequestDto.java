package com.customerManagementApi.models;

import com.customerManagementApi.models.enums.GENDER;
import com.customerManagementApi.models.validators.ValidDate;
import com.customerManagementApi.models.validators.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {

    @NotBlank(message = "Customer Name is mandatory")
    @Size(min = 1,max = 100)
    String name;

    @NotNull(message = "Age is mandatory")
    @Positive(message = "Age must be positive")
    int age;

    @NotNull(message = "Date of Birth is mandatory")
    @ValidDate(past = true)
    String dob;

    @Size(max = 200,message = "Address should not be more than 200 characters long")
    String address;

    @ValueOfEnum(enumClass = GENDER.class)
    String gender;



}
