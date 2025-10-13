package com.game.microbook.order.web.controllers;

import com.game.microbook.order.domain.OrderService;
import com.game.microbook.order.domain.SecurityService;
import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.domain.model.CreateOrderResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        var user = securityService.getLoginUser();
        log.info("Creating order for user={}", user);
        return orderService.createOrder(user, createOrderRequest);
    }
}
