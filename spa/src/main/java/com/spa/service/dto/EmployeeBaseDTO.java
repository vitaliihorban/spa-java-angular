package com.spa.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBaseDTO {

    @ApiModelProperty
    @NotBlank
    private String name;

    @ApiModelProperty(position = 1)
    @NotNull
    private Boolean isActive;

    @ApiModelProperty(position = 2)
    @NotNull
    private DepartmentExtendedDTO department;
}
