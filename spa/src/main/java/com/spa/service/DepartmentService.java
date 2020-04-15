package com.spa.service;

import com.spa.domain.Sort;
import com.spa.service.dto.DepartmentBaseDTO;
import com.spa.service.dto.DepartmentExtendedDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentExtendedDTO save(DepartmentBaseDTO departmentBaseDTO);

    List<DepartmentExtendedDTO> findAllDepartments(Integer limit, Integer offset, Sort sort);
}
