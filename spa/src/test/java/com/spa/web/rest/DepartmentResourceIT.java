package com.spa.web.rest;

import com.spa.SpaBeApplication;
import com.spa.domain.Department;
import com.spa.repository.DepartmentRepository;
import com.spa.service.DepartmentService;
import com.spa.service.dto.DepartmentExtendedDTO;
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

import static com.spa.TestUtils.mapContentToObjectList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link com.spa.web.rest.DepartmentResource} REST controller.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpaBeApplication.class})
public class DepartmentResourceIT {
    private static final String DEPARTMENT_NAME = "DEPARTMENT-TEST";

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restAlbumMockMvc;

    private static Department department;

    @BeforeEach
    public void initTest() {
        MockitoAnnotations.initMocks(this);
        final DepartmentResource albumResource = new DepartmentResource(departmentService);
        this.restAlbumMockMvc = MockMvcBuilders.standaloneSetup(albumResource)
                .setMessageConverters(jacksonMessageConverter).build();


        department = Department.builder().name(DEPARTMENT_NAME).build();
        Integer departmentId = departmentRepository.save(department);
        department.setId(departmentId);

    }

    @Test
    public void findAllDepartmentsTest() throws Exception {
        MvcResult mvcResult = restAlbumMockMvc.perform(get("/api/departments")
                .with(user("spa_user").password("spa_pass")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<DepartmentExtendedDTO> employeeExtendedDTOList = mapContentToObjectList(mvcResult, DepartmentExtendedDTO.class);
        assertThat(employeeExtendedDTOList.contains(department));
        departmentRepository.delete(department.getId());
    }

}
