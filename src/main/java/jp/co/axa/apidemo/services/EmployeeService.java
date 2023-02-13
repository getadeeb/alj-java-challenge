package jp.co.axa.apidemo.services;

import java.awt.print.Pageable;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface EmployeeService {

    Page<Employee> retrieveEmployees(PageRequest paging);

    Employee getEmployee(Long employeeId);

    Employee saveEmployee(Employee employee);

    Employee deleteEmployee(Long employeeId);

    Employee updateEmployee(Employee employee);
}