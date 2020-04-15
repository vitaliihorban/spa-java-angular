package com.spa.service.impl;

import com.spa.repository.DepartmentRepository;
import com.spa.service.DepartmentService;
import com.spa.service.dto.DepartmentBaseDTO;
import com.spa.service.dto.DepartmentExtendedDTO;
import com.spa.service.mapper.DepartmentMapper;
import com.spa.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper,
                                 DepartmentRepository departmentRepository) {
        this.departmentMapper = departmentMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentExtendedDTO save(DepartmentBaseDTO departmentBaseDTO) {
        Integer departmentId = departmentRepository.save(departmentMapper.departmentBaseDTOToDepartment(departmentBaseDTO));
        return departmentMapper.departmentToDepartmentExtendedDTO(departmentRepository.findOne(departmentId));

    }

    @Override
    public List<DepartmentExtendedDTO> findAllDepartments(Integer limit, Integer offset, Sort sort) {
        return departmentRepository.findAll(limit, offset, sort).stream()
                .map(departmentMapper::departmentToDepartmentExtendedDTO).collect(Collectors.toList());
    }
}
