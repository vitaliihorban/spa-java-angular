package com.spa.service.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeBaseDTOTest extends AbstractEntityUnitTest {
    @Test
    public void testAddressRequiredFieldsAreInitialized() {
        EmployeeBaseDTO address = EmployeeBaseDTO.builder().name("TEST-NAME").isActive(true)
                .department(DepartmentExtendedDTO.builder().id(1).build()).build();

        Set<ConstraintViolation<EmployeeBaseDTO>> violations = getValidator().validate(address);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testAddressRequiredFieldsAreNotInitialized() {
        EmployeeBaseDTO address = EmployeeBaseDTO.builder().build();

        List<String> validationFailedProperties =
                getValidator().validate(address).stream().map(c -> c.getPropertyPath().toString())
                        .collect(Collectors.toList());

        assertThat(validationFailedProperties).hasSize(3);
        assertThat(validationFailedProperties).contains("name", "isActive", "department");
    }
}
