package scottlogic.javatraining.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                                                              @NotNull HttpHeaders headers, @NotNull HttpStatus status,
                                                                                                              @NotNull WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                                           @NotNull HttpHeaders headers, HttpStatus status,
                                                                           @NotNull WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        String errorMessage = refineErrorMessage(Objects.requireNonNull(exception.getMessage()));
        body.put("errors", errorMessage);
        return new ResponseEntity<>(body, headers, status);
    }


    private String refineErrorMessage(@NotNull String message) {
        String type = message.contains("userId")
                ? "User ID"
                : message.contains("Exchange")
                ? "Exchange"
                : message.contains("Market")
                ? "Market" : "";

        if (type.isEmpty()) {
            return "Something went wrong with your order request";
        } else {
            return String.format("Invalid %s field in Order request", type);
        }
    }
}


