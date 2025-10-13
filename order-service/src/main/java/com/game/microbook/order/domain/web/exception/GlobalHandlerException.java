package com.game.microbook.order.domain.web.exception;

import com.game.microbook.order.domain.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalHandlerException {

    @ExceptionHandler(value = OrderNotFoundException.class)
    ResponseEntity<ResponseError> orderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError(e.getMessage()));
    }
}
