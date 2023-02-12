package jp.co.axa.apidemo;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@Sql({"classpath:seeds/clear_all_data.sql"})
public class EmployeeBackEndApplicationTests {


	@Test
	@DisplayName("Access fails when no valid user is present")
	void unauthorizedAccess(){

	}

	@Test
	@DisplayName("Create Fails Invalid Parameters Passed")
	void createFailsOnInvalidParameters() {

	}

	@Test
	@DisplayName("Create succeeds on Valid parameters")
	void createSucceedsOnValidParameters(){

	}

	@Test
	@DisplayName("Update fails on invalid parameters")
	void updateFailsOnInvalidParameters(){

	}

	@Test
	@DisplayName("Update succeeds on valid parameters")
	void updateSucceedsOnValidParameters(){

	}

	@Test
	@DisplayName("succeeds on listing employees")
	void succeedsListEmployees(){

	}


	@Test
	@DisplayName("Succeeds on getting single employee")
	void succeedsOnGettingOneEmployee(){

	}

	@Test
	@DisplayName("Fails on getting single employee with invalid id")
	void failsOnGettingEmployeeWithInvalidId(){

	}

	@Test
	@DisplayName("Succeeds on deletion of employee with valid id")
	void succeedsOnDeletionValidId(){

	}

	@Test
	@DisplayName("Fails on deletion of employee with invalid employee Id")
	void failsOnDeletionInvalidId(){

	}
}
