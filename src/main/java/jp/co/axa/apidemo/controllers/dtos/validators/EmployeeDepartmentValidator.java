package jp.co.axa.apidemo.controllers.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import jp.co.axa.apidemo.controllers.dtos.params.EmployeeParam;
import jp.co.axa.apidemo.entities.Employee.Department;
import jp.co.axa.apidemo.utils.ConstraintValidatorUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class EmployeeDepartmentValidator implements
    ConstraintValidator<ValidEmployeeDepartment, Object> {

  @Override
  public void initialize(ValidEmployeeDepartment constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Object employeeParamObject,
      ConstraintValidatorContext constraintValidatorContext) {
    EmployeeParam employeeParam = (EmployeeParam) employeeParamObject;
    String employeeDepartment = employeeParam.getDepartment();

    for (Department department : Department.values()) {
      if (department.name().equals(employeeDepartment)) {
        return true;
      }
    }
    ConstraintValidatorUtility.addConstraint(constraintValidatorContext,
        "INVALID_EMPLOYEE_DEPARTMENT", employeeDepartment);
    return false;
  }
}
