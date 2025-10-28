package com.game.microbook.order.domain;

import com.game.microbook.order.OrderApplicationProperties;
import com.game.microbook.order.domain.model.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final OrderApplicationProperties applicationProperties;
    private final OrderApplicationProperties orderApplicationProperties;

    public void publish(OrderCreatedEvent event) {
        this.send(applicationProperties.newOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(orderApplicationProperties.orderEventsExchange(), routingKey, payload);
    }
}
