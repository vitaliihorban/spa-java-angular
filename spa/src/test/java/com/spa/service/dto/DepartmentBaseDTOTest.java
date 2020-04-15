package com.spa.service.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DepartmentBaseDTOTest extends AbstractEntityUnitTest {
    @Test
    public void testAddressRequiredFieldsAreInitialized() {
        DepartmentBaseDTO address = DepartmentBaseDTO.builder().name("TEST-NAME").build();

        Set<ConstraintViolation<DepartmentBaseDTO>> violations = getValidator().validate(address);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testAddressRequiredFieldsAreNotInitialized() {
        DepartmentBaseDTO address = DepartmentBaseDTO.builder().build();

        List<String> validationFailedProperties =
                getValidator().validate(address).stream().map(c -> c.getPropertyPath().toString())
                        .collect(Collectors.toList());

        assertThat(validationFailedProperties).hasSize(1);
        assertThat(validationFailedProperties).contains("name");
    }
}
