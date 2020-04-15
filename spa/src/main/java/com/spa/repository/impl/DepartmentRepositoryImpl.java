package com.spa.repository.impl;

import com.spa.domain.Department;
import com.spa.repository.DepartmentRepository;
import com.spa.repository.rowmapper.DepartmentRowMapper;
import com.spa.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private static final String CREATE_DEPARTMENT_QUERY = "INSERT INTO department (name) values (?)";
    private static final String UPDATE_DEPARTMENT_QUERY = "UPDATE department SET name=? WHERE id=?";
    private static final String DELETE_DEPARTMENT_QUERY = "DELETE FROM department WHERE id=?";
    private static final String FIND_ONE_DEPARTMENT_QUERY = "SELECT * FROM department WHERE id = ?";
    private static final String FIND_ALL_DEPARTMENT_QUERY = "SELECT * from department order by ? ? limit ? offset ? ";

    private final JdbcTemplate jdbcTemplate;
    private final DepartmentRowMapper departmentRowMapper;
    private final KeyHolder keyHolder;

    @Autowired
    public DepartmentRepositoryImpl(JdbcTemplate jdbcTemplate,
                                    DepartmentRowMapper departmentRowMapper,
                                    KeyHolder keyHolder) {
        this.jdbcTemplate = jdbcTemplate;
        this.departmentRowMapper = departmentRowMapper;
        this.keyHolder = keyHolder;
    }

    @Override
    public Integer save(Department entity) {
        jdbcTemplate.update(c -> {
            PreparedStatement ps = c.prepareStatement(CREATE_DEPARTMENT_QUERY, new String[]{"id"});
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(Department entity) {
        return jdbcTemplate.update(UPDATE_DEPARTMENT_QUERY, entity.getName(), entity.getId());
    }

    @Override
    public Integer delete(Integer id) {
        return jdbcTemplate.update(DELETE_DEPARTMENT_QUERY, id);
    }

    @Override
    public Department findOne(Integer id) {
        return jdbcTemplate.queryForObject(FIND_ONE_DEPARTMENT_QUERY, departmentRowMapper, id);
    }

    @Override
    public List<Department> findAll(Integer limit, Integer offset, Sort sort) {
        return jdbcTemplate.query(FIND_ALL_DEPARTMENT_QUERY, ps -> {
            ps.setString(1, sort.getField());
            ps.setString(2, sort.getDirection().name());
            ps.setInt(3, limit);
            ps.setInt(4, offset);
        }, departmentRowMapper);
    }
}
