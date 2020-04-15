package com.spa.web.rest;

import com.spa.SpaBeApplication;
import com.spa.domain.Department;
import com.spa.domain.Employee;
import com.spa.repository.DepartmentRepository;
import com.spa.repository.EmployeeRepository;
import com.spa.service.EmployeeService;
import com.spa.service.dto.EmployeeBaseDTO;
import com.spa.service.dto.EmployeeExtendedDTO;
import com.spa.service.mapper.EmployeeMapper;
import com.spa.web.rest.EmployeeResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.spa.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link com.spa.web.rest.EmployeeResource} REST controller.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpaBeApplication.class})
public class EmployeeResourceIT {
    private static final String EMPLOYEE_NAME = "TEST-NAME";
    private static final String DEPARTMENT_NAME = "DEPARTMENT-TEST";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restAlbumMockMvc;

    private static Department department;

    private static Employee employee;

    @BeforeEach
    public void initTest() {
        MockitoAnnotations.initMocks(this);
        final EmployeeResource albumResource = new EmployeeResource(employeeService);
        this.restAlbumMockMvc = MockMvcBuilders.standaloneSetup(albumResource)
                .setMessageConverters(jacksonMessageConverter).build();

        department = Department.builder().name(DEPARTMENT_NAME).build();
        Integer departmentId = departmentRepository.save(department);
        department.setId(departmentId);

        employee = Employee.builder().name(EMPLOYEE_NAME).isActive(true)
                .department(department).build();
    }

    @Test
    public void createEmployeeTest() throws Exception {
        EmployeeBaseDTO employeeBaseDTO = employeeMapper.employeeToEmployeeBaseDTO(employee);
        MvcResult mvcResult = restAlbumMockMvc.perform(post("/api/employees")
                .with(user("spa_user").password("spa_pass")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeBytes(employeeBaseDTO)))
                .andExpect(status().isCreated()).andReturn();

        EmployeeExtendedDTO employeeExtendedDTO = mapObject(mvcResult, EmployeeExtendedDTO.class);

        Employee employeeDB = employeeRepository.findOne(employeeExtendedDTO.getId());

        assertThat(employeeDB.getName()).isEqualTo(employee.getName());
        assertThat(employeeDB.getIsActive()).isEqualTo(employee.getIsActive());
        assertThat(employeeDB.getDepartment()).isEqualTo(employee.getDepartment());
    }


    @Test
    public void findAllEmployeesTest() throws Exception {

        Integer employeeId = employeeRepository.save(employee);
        employee.setId(employeeId);

        MvcResult mvcResult = restAlbumMockMvc.perform(get("/api/employees")
                .with(user("spa_user").password("spa_pass")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<EmployeeExtendedDTO> employeeExtendedDTOList = mapContentToObjectList(mvcResult, EmployeeExtendedDTO.class);
        assertThat(employeeExtendedDTOList.contains(employee));
    }

    @Test
    public void findAllEmployeesByNameTest() throws Exception {

        Integer employeeId = employeeRepository.save(employee);
        employee.setId(employeeId);

        MvcResult mvcResult = restAlbumMockMvc.perform(get("/api/employees?name=TEST")
                .with(user("spa_user").password("spa_pass")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<EmployeeExtendedDTO> employeeExtendedDTOList = mapContentToObjectList(mvcResult, EmployeeExtendedDTO.class);
        assertThat(employeeExtendedDTOList.contains(employee));
    }

    @Test
    public void findEmployeeByIdTest() throws Exception {
        Integer employeeId = employeeRepository.save(employee);
        employee.setId(employeeId);

        MvcResult mvcResult = restAlbumMockMvc.perform(get("/api/employees/{id}", employeeId)
                .with(user("spa_user").password("spa_pass")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        EmployeeExtendedDTO employeeExtendedDTO = mapObject(mvcResult, EmployeeExtendedDTO.class);
        assertThat(employeeExtendedDTO).isEqualToIgnoringGivenFields(employee, "department");
        assertThat(employeeExtendedDTO.getDepartment()).isEqualToIgnoringGivenFields(employee.getDepartment());
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        Integer employeeId = employeeRepository.save(employee);
        employee.setId(employeeId);
        EmployeeBaseDTO employeeBaseDTO = employeeMapper.employeeToEmployeeBaseDTO(employee);
        employeeBaseDTO.setIsActive(false);
        employeeBaseDTO.setName("UPDATED");

        MvcResult mvcResult = restAlbumMockMvc.perform(put("/api/employees/{id}", employeeId)
                .with(user("spa_user").password("spa_pass")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeBytes(employeeBaseDTO)))
                .andExpect(status().isOk()).andReturn();

        EmployeeExtendedDTO employeeExtendedDTO = mapObject(mvcResult, EmployeeExtendedDTO.class);
        assertThat(employeeExtendedDTO.getName()).isEqualTo(employeeBaseDTO.getName());
        assertThat(employeeExtendedDTO.getIsActive()).isEqualTo(employeeBaseDTO.getIsActive());
        assertThat(employeeExtendedDTO.getDepartment().getName()).isEqualTo(employeeBaseDTO.getDepartment().getName());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        Integer employeeId = employeeRepository.save(employee);

        restAlbumMockMvc.perform(delete("/api/employees/{id}", employeeId)
                .with(user("spa_user").password("spa_pass")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(employeeRepository.findOne(employeeId)).isNull();
    }
}
