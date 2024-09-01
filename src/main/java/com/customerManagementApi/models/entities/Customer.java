package com.customerManagementApi.models.entities;

import com.customerManagementApi.models.validators.ValueOfEnum;
import com.customerManagementApi.models.CustomerRequestDto;
import com.customerManagementApi.models.enums.GENDER;
import com.customerManagementApi.models.entities.entityListeners.CustomerEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
@Data
@NoArgsConstructor
@EntityListeners(CustomerEntityListener.class)
public class Customer{

    public static final String datePattern = "yyyy-MM-dd";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long customerId;

    @NotBlank()
    @Size(min = 1,max = 100)
    String name;

    @NotNull()
    @Positive()
    int age;

    @NotNull()
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = datePattern)
    LocalDate dob;

    @Size(max = 200)
    String address;

    @ValueOfEnum(enumClass = GENDER.class)
    String gender;

    public Customer(CustomerRequestDto customerRequestDto){
        name = customerRequestDto.getName();
        age = customerRequestDto.getAge();
        dob = LocalDate.parse(customerRequestDto.getDob(),DateTimeFormatter.ofPattern(datePattern));
        address = customerRequestDto.getAddress();
        gender = customerRequestDto.getGender();
    }

}
