package jp.co.axa.apidemo.controllers.dtos.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmployeeDepartmentValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmployeeDepartment {
  String message() default "INVALID_EMPLOYEE_DEPARTMENT";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
