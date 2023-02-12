package jp.co.axa.apidemo.controllers.dtos.params;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class EmployeePaginationParam {

  private static final int DEFAULT_PAGE = 0;
  private static final int DEFAULT_SIZE = 10;

  private int page;
  private int size;

  public EmployeePaginationParam(Integer page, Integer size) {
    this.page = Optional.ofNullable(page).orElse(DEFAULT_PAGE);
    this.size = Optional.ofNullable(size).orElse(DEFAULT_SIZE);
  }

  public PageRequest getPageableForDB() {
    return PageRequest.of(this.page, this.size);
  }

}
