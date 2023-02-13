package jp.co.axa.apidemo.controllers.dtos.params;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import jp.co.axa.apidemo.controllers.dtos.validators.ValidEmployeeDepartment;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.Employee.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeParam {

  /*1. allow characters from any language,
   * 2. do not allow special characters
   * 3. allow white spaces in names
   * 4. Numbers not allowed
   * */

  @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Must not contains numbers or special characters")
  @NotBlank String name;

  /*salary has a min and max range*/
  @Range(min = 0, max = 99999999)
  @NotNull Integer salary;

  /*
   * department should be one of the valid department constants.
   * */

  String  department;

  /*
   * function to build entity for update
   * */
  public Employee getEmployeeToUpdate(Long id) {
    Employee employee = this.getEmployee();
    employee.setId(id);
    employee.setUpdatedAt(ZonedDateTime.now());
    return employee;
  }

  /*
   * function to build entity for insertion
   * */
  public Employee buildNewEmployee() {
    Employee employee = getEmployee();
    employee.setCreatedAt(ZonedDateTime.now());
    return employee;
  }

  private Employee getEmployee() {
    return Employee.builder()
        .name(StringUtils.normalizeSpace(name))
        .salary(salary)
        .department(Department.valueOf(department))
        .build();
  }
}
