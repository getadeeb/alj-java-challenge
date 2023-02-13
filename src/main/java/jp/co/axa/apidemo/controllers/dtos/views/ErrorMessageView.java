package jp.co.axa.apidemo.controllers.dtos.views;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorMessageView {

  private final String errorCode;
  private final String exceptionMessage;

  public static ErrorMessageView from(String errorCode, String exceptionMessage) {
    return new ErrorMessageView(errorCode, exceptionMessage);
  }
}
