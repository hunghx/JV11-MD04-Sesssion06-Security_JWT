package ra.security.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.security.exception.NotFoundException;
import ra.security.model.dto.response.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class APIControllerAdvice {
    // Bắt va xu lý lỗi NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,ErrorResponse>> handleNotFoundException(NotFoundException e) {
        ErrorResponse err = new ErrorResponse();
        err.setCode(404);
        err.setMessage(e.getMessage());
        err.setDetails("");
        Map<String,ErrorResponse> map = new HashMap<>();
        map.put("error", err);
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,ErrorResponse>> handleMethodArgumentException(MethodArgumentNotValidException e){
        Map<String, String> details = new HashMap<>();
        e.getFieldErrors().forEach(fieldError -> {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        ErrorResponse err = new ErrorResponse();
        err.setCode(400);
        err.setMessage("Invalid Parameter");
        err.setDetails(details);
        Map<String,ErrorResponse> map = new HashMap<>();
        map.put("error", err);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
