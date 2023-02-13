package jp.co.axa.apidemo.controllers.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.utils.ConstraintValidatorUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class EmployeeIdValidator implements ConstraintValidator<ValidEmployeeId, Long> {

  private final EmployeeRepository employeeRepository;

  @Override
  public void initialize(ValidEmployeeId constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Long employeeId, ConstraintValidatorContext constraintValidatorContext) {
    if (!employeeRepository.existsById(employeeId)) {
      ConstraintValidatorUtility.addConstraint(constraintValidatorContext, "INVALID_EMPLOYEE_ID",
          employeeId.toString());
      return false;
    }
    return true;
  }
}
