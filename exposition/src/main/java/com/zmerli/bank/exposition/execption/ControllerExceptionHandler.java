package com.zmerli.bank.exposition.execption;

import com.zmerli.bank.exposition.execption.model.AccountNotFoundException;
import com.zmerli.bank.exposition.execption.model.ErrorModelV1;
import com.zmerli.bank.exposition.execption.model.InsufficientFundException;
import com.zmerli.bank.exposition.execption.model.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorModelV1 accountResourceNotFoundException(AccountNotFoundException ex, WebRequest request) {

        return new ErrorModelV1(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(InsufficientFundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorModelV1 InsufficientFundException(InsufficientFundException ex, WebRequest request) {

        return new ErrorModelV1(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorModelV1 handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("handleMethodArgumentNotValid() - MethodArgumentNotValidException: {}", ex.getMessage());

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        return new ErrorModelV1(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors.toString(), request.getDescription(false));

    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorModelV1 resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        return new ErrorModelV1(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorModelV1 globalExceptionHandler(Exception ex, WebRequest request) {

        return new ErrorModelV1(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }
}
