package com.spa.service.mapper;

import com.spa.domain.Department;
import com.spa.service.dto.DepartmentBaseDTO;
import com.spa.service.dto.DepartmentExtendedDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentExtendedDTO departmentToDepartmentExtendedDTO(Department department);
    Department departmentExtendedDTOToDepartment(DepartmentExtendedDTO departmentExtendedDTO);
    Department departmentBaseDTOToDepartment(DepartmentBaseDTO departmentBaseDTO);
}
