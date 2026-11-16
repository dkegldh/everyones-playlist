package com.jin.everyonesplaylist.domain.user.exception;

import com.jin.everyonesplaylist.global.error.CustomException;
import java.util.Map;

public class UserException extends CustomException {

  public UserException(UserErrorCode errorCode) {
    super(errorCode);
  }

  public UserException(UserErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
