package com.game.microbook.order;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orders")
public record OrderApplicationProperties(
        String orderEventsExchange,
        String newOrdersQueue,
        String deliveredOrdersQueue,
        String cancelledOrdersQueue,
        String errorOrdersQueue) {}
