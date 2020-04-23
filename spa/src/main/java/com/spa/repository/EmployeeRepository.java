package com.spa.repository;

import com.spa.domain.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeRepository extends AbstructCrudRepository<Integer, Employee> {

    int countAllEmployeeByName(String name);

    int countAllEmployee();

    Page<Employee> findAll(Integer page, Integer size);

    Page<Employee> findAllByName(String name, Integer page, Integer size);

}
