package com.example.orisimpl.controllers.handlers;


import com.example.orisimpl.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Обработчик возникающих ошибок.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    /**
     * Catch exceptions when JSON cannot be read (for example invalid field name or enum value)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ExceptionMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ExceptionMessage.builder()
                        .message(ex.getMessage())
                        .exceptionName(ex.getClass().getSimpleName())
                        .build();
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ExceptionMessage> handleServiceException(ServiceException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ExceptionMessage.builder()
                        .exceptionName(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build()
                );
    }

    /**
     * Формирует ответ на основе всех исключений {@link Exception}.
     *
     * @param exception исключение
     * @return сформированный на основе исключения ответ
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ExceptionMessage onAllExceptions(Exception exception) {
        return ExceptionMessage.builder()
                        .message(exception.getMessage())
                        .exceptionName(exception.getClass().getSimpleName())
                        .build();
    }
}