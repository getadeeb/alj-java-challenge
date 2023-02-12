package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import jp.co.axa.apidemo.controllers.dtos.params.EmployeePaginationParam;
import jp.co.axa.apidemo.controllers.dtos.params.EmployeeParam;
import jp.co.axa.apidemo.controllers.dtos.validators.ValidEmployeeDepartment;
import jp.co.axa.apidemo.controllers.dtos.validators.ValidEmployeeId;
import jp.co.axa.apidemo.controllers.dtos.views.EmployeeView;
import jp.co.axa.apidemo.controllers.dtos.views.ErrorMessageView;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Validated
/*
 * All End points are protected with Basic Spring security authentication.
 * Please set username and password environment variable on docker for
 * accessing this api.
 * */
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "All employees will be listed",
            response = EmployeeView.class),
    })
    public Page<EmployeeView> getEmployees(EmployeePaginationParam paging) {
        return toViewPage(employeeService.retrieveEmployees(
            paging.getPageableForDB()));
    }

    @GetMapping("/employees/{employeeId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Employee details will be displayed",
            response = EmployeeView.class),
    })
    public EmployeeView getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return EmployeeView.from(employeeService.getEmployee(employeeId));
    }

    @Transactional
    @PostMapping("/employees")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Employee created successfully",
            response = EmployeeView.class),
        @ApiResponse(code = 422, message = "Employee creation failed",
            response = ErrorMessageView.class),
    })
    public EmployeeView saveEmployee(
        @Valid @RequestBody @ValidEmployeeDepartment EmployeeParam param) {
        return EmployeeView.from(employeeService.saveEmployee(param.buildNewEmployee()));
    }

    @Transactional
    @DeleteMapping("/employees/{employeeId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Employee deleted successfully",
            response = EmployeeView.class),
        @ApiResponse(code = 422, message = "Employee deletion failed",
            response = ErrorMessageView.class),
    })
    public EmployeeView deleteEmployee(
        @PathVariable(name = "employeeId") @Valid @ValidEmployeeId Long employeeId) {
        return EmployeeView.from(employeeService.deleteEmployee(employeeId));
    }

    @Transactional
    @PutMapping("/employees/{employeeId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Employee updated successfully",
            response = EmployeeView.class),
        @ApiResponse(code = 422, message = "Employee update failed",
            response = ErrorMessageView.class),
    })

    public EmployeeView updateEmployee(
        @Valid @RequestBody @ValidEmployeeDepartment EmployeeParam param,
        @PathVariable(name = "employeeId") @Valid @ValidEmployeeId Long employeeId) {
        return EmployeeView.from(
            employeeService.updateEmployee(param.getEmployeeToUpdate(employeeId)));
    }

    private static Page<EmployeeView> toViewPage(Page<Employee> page) {
        return new PageImpl<>(toViewList(page.stream().collect(Collectors.toList())),
            page.getPageable(), page.getTotalElements());
    }

    private static List<EmployeeView> toViewList(List<Employee> list) {
        return list.stream().map(EmployeeView::from).collect(Collectors.toList());
    }
}
