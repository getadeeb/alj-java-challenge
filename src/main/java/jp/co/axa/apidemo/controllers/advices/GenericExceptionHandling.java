package jp.co.axa.apidemo.controllers.advices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import jp.co.axa.apidemo.controllers.dtos.views.ErrorMessageView;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GenericExceptionHandling {

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  void handle(EntityNotFoundException handled) {
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<List<ErrorMessageView>> handle(ConstraintViolationException handled) {
    List<ConstraintViolation> errorMessageList = new ArrayList<>(handled.getConstraintViolations());
    List<ErrorMessageView> errorMessageViewList = new ArrayList<>();
    for (ConstraintViolation errorMessage : errorMessageList) {
      String errorCode = "";
      for (Node node : errorMessage.getPropertyPath()) {
        errorCode = node.getName();
      }
      errorMessageViewList.add(ErrorMessageView.from(errorCode, errorMessage.getMessage()));
    }
    Collections.sort(errorMessageViewList,
        Comparator.comparing(ErrorMessageView::getErrorCode));
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(errorMessageViewList);
  }

  @SneakyThrows
  @ExceptionHandler(BindException.class)
  ResponseEntity<List<ErrorMessageView>> handle(BindException handled) {
    List<ErrorMessageView> errorMessageViewList = new ArrayList<>();
    List<FieldError> fieldErrorList = handled.getFieldErrors();
    for (FieldError fieldError : fieldErrorList) {
      errorMessageViewList.add(
          ErrorMessageView.from(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    Collections.sort(errorMessageViewList,
        Comparator.comparing(ErrorMessageView::getErrorCode));
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessageViewList);
  }

  @SneakyThrows
  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<List<ErrorMessageView>> handle(MethodArgumentNotValidException handled) {
    List<ErrorMessageView> errorMessageViewList = new ArrayList<>();
    List<FieldError> fieldErrorList = handled.getBindingResult().getFieldErrors();
    for (FieldError fieldError : fieldErrorList) {
      errorMessageViewList.add(
          ErrorMessageView.from(fieldError.getCode(), fieldError.getField())
      );
    }
    Collections.sort(errorMessageViewList,
        Comparator.comparing(ErrorMessageView::getErrorCode));
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessageViewList);
  }
}
