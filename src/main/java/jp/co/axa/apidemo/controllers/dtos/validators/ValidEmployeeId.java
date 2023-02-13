package jp.co.axa.apidemo.controllers.dtos.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmployeeIdValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmployeeId {

  String message() default "INVALID_EMPLOYEE_ID";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
