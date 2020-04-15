package com.spa.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private Integer id;
    private String name;
    private Boolean isActive;
    private Department department;
}
