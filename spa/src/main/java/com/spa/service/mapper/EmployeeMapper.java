package com.spa.service.mapper;

import com.spa.domain.Employee;
import com.spa.service.dto.EmployeeBaseDTO;
import com.spa.service.dto.EmployeeExtendedDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface EmployeeMapper {

    Employee employeeBaseDTOToEmployee(EmployeeBaseDTO employeeDTO);

    Employee employeeBaseDTOToEmployee(Integer id, EmployeeBaseDTO employeeDTO);

    EmployeeExtendedDTO employeeToEmployeeExtendDTO(Employee employee);

    EmployeeBaseDTO employeeToEmployeeBaseDTO(Employee employee);



}
