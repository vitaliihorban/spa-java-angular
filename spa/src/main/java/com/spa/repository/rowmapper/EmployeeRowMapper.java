package com.spa.repository.rowmapper;

import com.spa.domain.Department;
import com.spa.domain.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Employee.builder()
                .id(rs.getInt("employee.id"))
                .isActive(rs.getBoolean("employee.is_active"))
                .name(rs.getString("employee.name"))
                .department(
                        Department.builder()
                                .name(rs.getString("department.name"))
                                .id(rs.getInt("department.id"))
                                .build())
                .build();
    }
}
