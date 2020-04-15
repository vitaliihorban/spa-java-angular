package com.spa.repository.rowmapper;

import com.spa.domain.Department;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DepartmentRowMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Department.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
