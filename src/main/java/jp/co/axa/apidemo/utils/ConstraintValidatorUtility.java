package jp.co.axa.apidemo.utils;

import javax.validation.ConstraintValidatorContext;

public class ConstraintValidatorUtility {

  public static void addConstraint(ConstraintValidatorContext context, String errorCode,
      String errorMessage) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode(errorCode)
        .addConstraintViolation();
  }
}
