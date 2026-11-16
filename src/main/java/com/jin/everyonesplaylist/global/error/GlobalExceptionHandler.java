package com.jin.everyonesplaylist.global.error;

import com.jin.everyonesplaylist.global.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
    ErrorCode errorCode = e.getErrorCode();
    log.warn("[CustomException] Type: {}, Code: {}, Message: {}, Details: {}", e.getClass().getSimpleName(), errorCode.getCode(), e.getMessage(), LogMaskingUtils.maskSensitiveDetails(e.getDetails()));
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage(), e.getDetails()));
  }

  // @Valid 검증 실패 (요청 body)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationException(
      MethodArgumentNotValidException e) {
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      if(!fieldError.contains(Throwable.class)) {
        continue;
      }
      CustomException cause = findCustomExceptionCause(fieldError.unwrap(Throwable.class));
      if(cause != null) {
        return handleCustomException(cause);
      }
    }

    String message = e.getBindingResult().getFieldErrors().stream()
        .findFirst()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
        .orElse(CommonErrorCode.INVALID_INPUT.getMessage());
    log.warn("[Validation failed] Message: {}", message);
    return ResponseEntity
        .status(CommonErrorCode.INVALID_INPUT.getStatus())
        .body(ApiResponse.error(CommonErrorCode.INVALID_INPUT.getCode(), message));
  }

  // 커스텀 Converter(예: sortBy 카멜케이스 변환)가 던진 CustomException은
  // DataBinder가 TypeMismatchException -> ConversionFailedException 순으로 감싸서 바인딩 오류로 만든다.
  // 원래의 도메인 에러 코드/메시지를 응답에 살리기 위해 원인 체인을 직접 순회한다.
  private CustomException findCustomExceptionCause(Throwable throwable) {
    Throwable current = throwable;
    while (current != null) {
      if(current instanceof CustomException customException) {
        return customException;
      }
      current = current.getCause();
    }
    return null;
  }

  // @Valid 검증 실패 (쿼리 파라미터, path variable 등)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
      ConstraintViolationException e) {
    log.warn("[ConstraintViolation] Message: {}", e.getMessage());
    return ResponseEntity
        .status(CommonErrorCode.INVALID_INPUT.getStatus())
        .body(ApiResponse.error(CommonErrorCode.INVALID_INPUT.getCode(), e.getMessage()));
  }

  // 예상 못한 모든 예외
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    log.error("[Unhandled exception] Message: {}", e.getMessage(), e);
    CommonErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
  }
}
