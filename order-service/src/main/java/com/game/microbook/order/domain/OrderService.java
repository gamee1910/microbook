package com.game.microbook.order.domain;

import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.domain.model.CreateOrderResponse;
import com.game.microbook.order.domain.model.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderEventService orderEventService;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public CreateOrderResponse createOrder(String user, CreateOrderRequest request) {
        orderValidator.validate(request);

        OrderEntity orderEntity = OrderMapper.convertToEntity(request);

        orderEntity.setUsername(user);

        OrderEntity entity = orderRepository.save(orderEntity);

        log.info("Order created: {}", entity);

        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(entity);

        orderEventService.save(orderCreatedEvent);

        return new CreateOrderResponse(entity.getOrderNumber());
    }
}
