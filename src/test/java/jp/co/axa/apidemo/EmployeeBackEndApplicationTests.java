package jp.co.axa.apidemo;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import jp.co.axa.apidemo.controllers.dtos.params.EmployeeParam;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.Employee.Department;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@Sql({"classpath:seeds/clear_all_data.sql"})
public class EmployeeBackEndApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ObjectMapper objectMapper;

	String createEmployeeParam(String name, String department, Integer salary)
			throws JsonProcessingException {
		EmployeeParam employeeParam = EmployeeParam.builder()
				.department(department)
				.salary(salary)
				.name(name)
				.build();
		return objectMapper.writeValueAsString(employeeParam);
	}

	private MockHttpServletRequestBuilder requestForCreate(String param) {
		return post("/api/v1/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(param);
	}

	private MockHttpServletRequestBuilder requestForUpdate(String param, String id) {
		return put("/api/v1/employees/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(param);
	}

	private MockHttpServletRequestBuilder requestForGet(String id) {
		return get("/api/v1/employees/" + id)
				.contentType(MediaType.APPLICATION_JSON);
	}

	private MockHttpServletRequestBuilder requestForList(String page, String size) {
		return get("/api/v1/employees?page="+page+"&size="+size)
				.contentType(MediaType.APPLICATION_JSON);
	}

	private MockHttpServletRequestBuilder requestForDelete(String id) {
		return delete("/api/v1/employees/" + id)
				.contentType(MediaType.APPLICATION_JSON);
	}

	void createEmployee(String employeeName, Integer salary, String department, Long employeeId) {
		employeeRepository.saveAndFlush(
				Employee.builder()
						.id(employeeId)
						.name(employeeName)
						.salary(salary)
						.department(Department.valueOf(department))
						.createdAt(ZonedDateTime.now())
						.updatedAt(ZonedDateTime.now())
						.build()
		);
	}

/*	@BeforeEach*/
	void setupDB() {
		employeeRepository.deleteAll();
		employeeRepository.flush();
	}

	@Test
	/*Create Fails Invalid Parameters Passed*/
	public void createFailsOnInvalidParameters() throws Exception {
		String param = createEmployeeParam("INVALID_NAME_123",
				"TECHNOLOGY", -5);
		MockHttpServletRequestBuilder request = requestForCreate(param);
		mockMvc.perform(request).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$[0].errorCode", is("Pattern")))
				.andExpect(jsonPath("$[0].exceptionMessage", is("name")))
				.andExpect(jsonPath("$[1].errorCode", is("Range")))
				.andExpect(jsonPath("$[1].exceptionMessage", is("salary")))
		;

		String paramTwo = createEmployeeParam("VALID NAME", "INVALID_DEPT", 5000);
		MockHttpServletRequestBuilder requestTwo = requestForCreate(paramTwo);
		mockMvc.perform(requestTwo).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$[0].errorCode", is("INVALID_EMPLOYEE_DEPARTMENT")))
				.andExpect(jsonPath("$[0].exceptionMessage", is("INVALID_DEPT")))
		;

	}

	@Test
	/*Create succeeds on Valid parameters*/
	public void createSucceedsOnValidParameters() throws Exception {
		String param = createEmployeeParam("EMPLOYEE NAME", "PRODUCT", 5000);
		MockHttpServletRequestBuilder request = requestForCreate(param);
		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("EMPLOYEE NAME")))
				.andExpect(jsonPath("$.department", is("PRODUCT")))
				.andExpect(jsonPath("$.salary", is(5000)))
		;
	}

	@Test
	/*Update fails on invalid parameters*/
	public void updateFailsOnInvalidParameters() throws Exception {
		String param = createEmployeeParam("EMPLOYEE NAME", "PRODUCT", 5000);
		MockHttpServletRequestBuilder request = requestForCreate(param);
		mockMvc.perform(request).andExpect(status().isOk());

		String updatedParam = createEmployeeParam("INVALID_NAME_123",
				"TECHNOLOGY", -5);
		MockHttpServletRequestBuilder updateRequest = requestForUpdate(updatedParam, "1");
		mockMvc.perform(updateRequest).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$[0].errorCode", is("Pattern")))
				.andExpect(jsonPath("$[0].exceptionMessage", is("name")))
				.andExpect(jsonPath("$[1].errorCode", is("Range")))
				.andExpect(jsonPath("$[1].exceptionMessage", is("salary")))
		;

		String updateParamTwo = createEmployeeParam("VALID NAME", "INVALID_DEPT", 5000);
		MockHttpServletRequestBuilder updateRequestTwo = requestForUpdate(updateParamTwo, "1");
		mockMvc.perform(updateRequestTwo).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$[0].errorCode", is("INVALID_EMPLOYEE_DEPARTMENT")))
				.andExpect(jsonPath("$[0].exceptionMessage", is("INVALID_DEPT")))
		;

	}

	@Test
	/*Update fails on invalid id*/
	public void updateFailsOnInvalidId() throws Exception {

		String updateParamTwo = createEmployeeParam("VALID NAME", "TECHNOLOGY", 5000);
		MockHttpServletRequestBuilder updateRequestTwo = requestForUpdate(updateParamTwo, "200");
		mockMvc.perform(updateRequestTwo).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$[0].errorCode", is("INVALID_EMPLOYEE_ID")))
				.andExpect(jsonPath("$[0].exceptionMessage", is("200")))
		;

	}

	@Test
	/*Update succeeds on valid parameters*/
	public void updateSucceedsOnValidParameters() throws Exception {
		setupDB();
		createEmployee("VALID EMPLOYEE", 5000, Department.PRODUCT.name(), 3L);
		String param = createEmployeeParam("EMPLOYEE NAME", "PRODUCT", 5000);
		MockHttpServletRequestBuilder request = requestForCreate(param);
		mockMvc.perform(request).andExpect(status().isOk());
		String updatedParam = createEmployeeParam("EMPLOYEE NAME UPDATED",
				"TECHNOLOGY", 6000);
		MockHttpServletRequestBuilder updateRequest = requestForUpdate(updatedParam, "3");
		mockMvc.perform(updateRequest).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("EMPLOYEE NAME UPDATED")))
				.andExpect(jsonPath("$.department", is("TECHNOLOGY")))
				.andExpect(jsonPath("$.salary", is(6000)))
				.andExpect(jsonPath("$.id", is(3)));
	}

	@Test
	/*succeeds on listing employees*/
	public void succeedsListEmployees() throws Exception {
		setupDB();
		createEmployee("VALID EMPLOYEE", 5000, Department.PRODUCT.name(), 5L);
		createEmployee("VALID EMPLOYEE ANOTHER", 6000, Department.PRODUCT.name(), 6L);
		MockHttpServletRequestBuilder request = requestForList("0","1");
		MvcResult result= mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].name", is("VALID EMPLOYEE")))
				.andExpect(jsonPath("$.content[0].department", is("PRODUCT")))
				.andExpect(jsonPath("$.content[0].salary", is(5000)))
				.andExpect(jsonPath("$.content[0].id", is(5)))
				.andExpect(jsonPath("$.content[0].department", is("PRODUCT")))
				.andExpect(jsonPath("$.content[0].salary", is(5000)))
				.andExpect(jsonPath("$.totalElements", is(2)))
				.andExpect(jsonPath("$.last", is(false)))
				.andExpect(jsonPath("$.number", is(0)))
				.andReturn();
	}


	@Test
	/*Succeeds on getting single employee*/
	public void succeedsOnGettingOneEmployee() throws Exception {
		setupDB();
		createEmployee("VALID EMPLOYEE", 5000, Department.PRODUCT.name(), 1L);
		MockHttpServletRequestBuilder request = requestForGet("3");
		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("EMPLOYEE NAME UPDATED")))
				.andExpect(jsonPath("$.department", is("TECHNOLOGY")))
				.andExpect(jsonPath("$.salary", is(6000)))
		;
	}

	@Test
	/*Fails on getting single employee with invalid id*/
	public void failsOnGettingEmployeeWithInvalidId() throws Exception {
		MockHttpServletRequestBuilder request = requestForGet("200");
		mockMvc.perform(request).andExpect(status().isNotFound())
		;
	}

	@Test
	/*Succeeds on deletion of employee with valid id*/
	public void succeedsOnDeletionValidId() throws Exception {
		createEmployee("VALID EMPLOYEE", 5000, Department.PRODUCT.name(), 1L);
		createEmployee("VALID ANOTHER EMPLOYEE", 6000, Department.RESEARCH.name(), 2L);
		MockHttpServletRequestBuilder request = requestForDelete("1");
		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("VALID EMPLOYEE")))
				.andExpect(jsonPath("$.department", is("PRODUCT")))
				.andExpect(jsonPath("$.salary", is(5000)))
				.andExpect(jsonPath("$.id", is(1)))
		;
	}

	@Test
	/*Fails on deletion of employee with invalid employee Id*/
	public void failsOnDeletionInvalidId() throws Exception {
		MockHttpServletRequestBuilder request = requestForDelete("200");
		mockMvc.perform(request).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$[0].errorCode", is("INVALID_EMPLOYEE_ID")))
				.andExpect(jsonPath("$[0].exceptionMessage", is("200")))
		;

	}

}
