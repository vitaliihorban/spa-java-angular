package com.spa.web.rest;

import com.spa.domain.Sort;
import com.spa.service.DepartmentService;
import com.spa.service.dto.DepartmentBaseDTO;
import com.spa.service.dto.DepartmentExtendedDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@CrossOrigin
@Api(value = "Department Resource")
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @ApiOperation(value = "Create new department", response = DepartmentExtendedDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @PostMapping("/departments")
    public ResponseEntity<DepartmentExtendedDTO> createDepartment(
            @ApiParam(value = "Department object", required = true)
            @RequestBody DepartmentBaseDTO departmentBaseDTO) {
        return ResponseEntity.created(URI.create("/departments")).body(departmentService.save(departmentBaseDTO));

    }

    @ApiOperation(value = "View a list of all departments", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentExtendedDTO>> getAllDepartments(
            @ApiParam(value = "define limit amount of desired departments", required = true) @RequestParam(defaultValue = "10") int limit,
            @ApiParam(value = "define offset for desired departments", required = true) @RequestParam(defaultValue = "0") int offset,
            @ApiParam(value = "define field name by which list of departments should be sort", required = true) @RequestParam(defaultValue = "name") String sort,
            @ApiParam(value = "define sort order. e.g. ASC/DESC", required = true) @RequestParam(defaultValue = "ASC") String direction) {
        return ResponseEntity.ok(departmentService.findAllDepartments(limit, offset, Sort.of(sort, direction)));
    }
}
