package com.game.microbook.order.domain;

import com.game.microbook.order.clients.catalog.Product;
import com.game.microbook.order.clients.catalog.ProductServiceClient;
import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.domain.model.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class OrderValidator {

    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);
    private final ProductServiceClient productServiceClient;

    OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            Product product = productServiceClient.getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("product not found"));

            if (item.price().compareTo(product.price()) != 0) {
                log.error("product price not matching, Actual price: {}, received price: {}", item.price(), product.price());
                throw new InvalidOrderException("product price not match");
            }
        }
    }
}
