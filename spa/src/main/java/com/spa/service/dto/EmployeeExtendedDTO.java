package com.spa.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExtendedDTO {

    @ApiModelProperty(position = 0)
    private Integer id;

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private Boolean isActive;

    @ApiModelProperty(position = 3)
    private DepartmentExtendedDTO department;
}
