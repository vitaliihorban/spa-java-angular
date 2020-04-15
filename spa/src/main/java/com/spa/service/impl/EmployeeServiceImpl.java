package com.spa.service.impl;

import com.spa.repository.EmployeeRepository;
import com.spa.service.EmployeeService;
import com.spa.service.dto.EmployeeBaseDTO;
import com.spa.service.dto.EmployeeExtendedDTO;
import com.spa.service.mapper.EmployeeMapper;
import com.spa.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper,
                               EmployeeRepository employeeRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeExtendedDTO save(EmployeeBaseDTO employeeBaseDTO) {
        Integer employeeId = employeeRepository.save(employeeMapper.employeeBaseDTOToEmployee(employeeBaseDTO));
        return employeeMapper.employeeToEmployeeExtendDTO(employeeRepository.findOne(employeeId));
    }

    @Override
    public List<EmployeeExtendedDTO> findAll(Integer limit, Integer offset, Sort sort) {
        return employeeRepository.findAll(limit, offset, sort).stream()
                .map(employeeMapper::employeeToEmployeeExtendDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeExtendedDTO> findAllByName(String name, Integer page, Integer size) {
        return employeeRepository.findAllByName(name, page, size)
                .map(employeeMapper::employeeToEmployeeExtendDTO);
    }

    @Override
    public Page<EmployeeExtendedDTO> findAll( Integer page, Integer size) {
        return employeeRepository.findAll(page, size)
                .map(employeeMapper::employeeToEmployeeExtendDTO);
    }

    @Override
    public EmployeeExtendedDTO findById(Integer id) {
        return employeeMapper.employeeToEmployeeExtendDTO(employeeRepository.findOne(id));
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.delete(id);
    }

    @Override
    public EmployeeExtendedDTO update(Integer id, EmployeeBaseDTO employeeBaseDTO) {
        employeeRepository.update(employeeMapper.employeeBaseDTOToEmployee(id, employeeBaseDTO));
        return employeeMapper.employeeToEmployeeExtendDTO(employeeRepository.findOne(id));
    }
}
