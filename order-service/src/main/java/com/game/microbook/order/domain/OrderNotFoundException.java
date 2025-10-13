package com.game.microbook.order.domain;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException orderNotFoundException(String code) {
        return new OrderNotFoundException(String.format("Order with code %s does not exist", code));
    }
}
