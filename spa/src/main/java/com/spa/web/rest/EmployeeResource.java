package com.spa.web.rest;

import com.spa.service.EmployeeService;
import com.spa.service.dto.EmployeeBaseDTO;
import com.spa.service.dto.EmployeeExtendedDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@Api(value = "Employee Resource")
@RestController
@RequestMapping("/api")
public class EmployeeResource {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @ApiOperation(value = "Create new employee", response = EmployeeExtendedDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @PostMapping("/employees")
    public ResponseEntity<EmployeeExtendedDTO> save(
            @ApiParam(value = "Employee object", required = true)
            @RequestBody EmployeeBaseDTO employeeBaseDTO) {
        return ResponseEntity.created(URI.create("/employees")).body(employeeService.save(employeeBaseDTO));
    }

    @ApiOperation(value = "Get list of all employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @GetMapping("/employees")
    public Page<EmployeeExtendedDTO> findAllEmployees(
            @ApiParam(value = "define name by which will be performed search", required = true) @RequestParam(required = false) String name,
            @ApiParam(value = "define limit amount of desired departments", required = true) @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "define offset for desired departments", required = true) @RequestParam(defaultValue = "5") int size) {

        if (!StringUtils.isEmpty(name)) {
            return employeeService.findAllByName(name, page, size);
        }

        return employeeService.findAll(page, size);
    }


    @ApiOperation(value = "Get employee", response = EmployeeExtendedDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeExtendedDTO> findEmployeeById(
            @ApiParam(value = "Employee id ", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @ApiOperation(value = "Update employee", response = EmployeeExtendedDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeExtendedDTO> update(
            @ApiParam(value = "Employee id ", required = true) @PathVariable Integer id,
            @ApiParam(value = "Employee object", required = true) @RequestBody EmployeeBaseDTO employeeBaseDTO) {
        return ResponseEntity.ok(employeeService.update(id, employeeBaseDTO));
    }

    @ApiOperation(value = "Delete employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> delete(
            @ApiParam(value = "Employee id ", required = true)
            @PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
