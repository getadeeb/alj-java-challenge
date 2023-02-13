package jp.co.axa.apidemo.controllers.dtos.views;

import java.time.ZonedDateTime;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.Employee.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeView {

  Long id;
  String name;
  Integer salary;
  Department department;
  ZonedDateTime updatedAt;
  ZonedDateTime createdAt;

  public static EmployeeView from(Employee employee) {
    return EmployeeView.builder()
        .id(employee.getId())
        .name(employee.getName())
        .salary(employee.getSalary())
        .department(employee.getDepartment())
        .createdAt(employee.getCreatedAt())
        .updatedAt(employee.getUpdatedAt())
        .build();
  }
}
