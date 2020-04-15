package com.spa.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Department {
    private Integer id;
    private String name;
}
