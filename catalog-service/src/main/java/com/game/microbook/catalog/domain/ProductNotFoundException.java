package com.game.microbook.catalog.domain;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException productNotFoundException(String code) {
        return new ProductNotFoundException(String.format("Product with code %s does not exist", code));
    }
}
