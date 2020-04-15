package com.spa.repository.impl;

import com.spa.domain.Employee;
import com.spa.domain.enumeration.Direction;
import com.spa.repository.EmployeeRepository;
import com.spa.repository.rowmapper.EmployeeRowMapper;
import com.spa.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String CREATE_EMPLOYEE_QUERY = "INSERT INTO employee (name, is_active, department_id) values (?, ?, ?)";
    private static final String UPDATE_EMPLOYEE_QUERY = "UPDATE employee SET name=?, is_active=?, department_id=? WHERE id=?";
    private static final String DELETE_EMPLOYEE_QUERY = "DELETE FROM employee WHERE id=?";
    private static final String COUNT_ALL_EMPLOYEE_QUERY = "SELECT count(*) FROM employee";
    private static final String FIND_ONE_EMPLOYEE_QUERY = "SELECT * FROM employee INNER JOIN  department ON employee.department_id = department.id WHERE employee.id = ?";
    private static final String FIND_ALL_EMPLOYEE_QUERY = "SELECT * from employee INNER JOIN  department ON employee.department_id = department.id order by ? ? limit ? offset ? ";
    private static final String FIND_ALL_EMPLOYEE_BY_NAME_QUERY = "SELECT * from employee INNER JOIN  department ON employee.department_id = department.id WHERE employee.name LIKE ?  order by employee.name ASC LIMIT ? OFFSET ? ";

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper employeeRowMapper;
    private final KeyHolder keyHolder;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate,
                                  EmployeeRowMapper employeeRowMapper,
                                  KeyHolder keyHolder) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeRowMapper = employeeRowMapper;
        this.keyHolder = keyHolder;
    }

    @Override
    public Integer save(Employee entity) {
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(CREATE_EMPLOYEE_QUERY, new String[]{"id"});
            ps.setString(1, entity.getName());
            ps.setBoolean(2, true);
            ps.setInt(3, entity.getDepartment().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(Employee entity) {
        return jdbcTemplate.update(UPDATE_EMPLOYEE_QUERY, entity.getName(), entity.getIsActive(), entity.getDepartment().getId(), entity.getId());
    }

    @Override
    public Integer delete(Integer id) {
        return jdbcTemplate.update(DELETE_EMPLOYEE_QUERY, id);
    }

    @Override
    public Employee findOne(Integer id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ONE_EMPLOYEE_QUERY, employeeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Employee> findAll(Integer limit, Integer offset, Sort sort) {
        return jdbcTemplate.query(FIND_ALL_EMPLOYEE_QUERY, employeeRowMapper, "employee." + sort.getField(), sort.getDirection().name(), limit, offset);
    }

    @Override
    public int countAllEmployee() {
        return jdbcTemplate.queryForObject(COUNT_ALL_EMPLOYEE_QUERY, Integer.class);
    }

    @Override
    public Page<Employee> findAll(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        List<Employee> employees = jdbcTemplate.query(FIND_ALL_EMPLOYEE_QUERY, employeeRowMapper, "employee.name", Direction.ASC.name(), pageRequest.getPageSize(), pageRequest.getOffset());
        int employeesCount = countAllEmployee();

        return new PageImpl<>(employees, pageRequest, employeesCount);
    }

    @Override
    public Page<Employee> findAllByName(String name, Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        List<Employee> employees =  jdbcTemplate.query(FIND_ALL_EMPLOYEE_BY_NAME_QUERY, employeeRowMapper, name + "%", pageRequest.getPageSize(), pageRequest.getOffset());
        int employeesCount = countAllEmployee();

        return new PageImpl<>(employees, pageRequest, employeesCount);
    }
}
