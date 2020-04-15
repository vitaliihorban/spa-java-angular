package com.spa.service;

import com.spa.service.dto.EmployeeBaseDTO;
import com.spa.service.dto.EmployeeExtendedDTO;
import com.spa.domain.Sort;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeExtendedDTO save(EmployeeBaseDTO employeeBaseDTO);
    EmployeeExtendedDTO findById(Integer id);
    Page<EmployeeExtendedDTO> findAllByName(String name, Integer page, Integer size);
    Page<EmployeeExtendedDTO> findAll(Integer page, Integer size);
    List<EmployeeExtendedDTO> findAll(Integer limit, Integer offset, Sort sort);
    EmployeeExtendedDTO update(Integer id, EmployeeBaseDTO employeeBaseDTO);
    void delete(Integer id);
}
