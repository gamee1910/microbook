package com.game.microbook.order.domain;

import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.domain.model.CreateOrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    public CreateOrderResponse createOrder(String user, CreateOrderRequest request) {
        orderValidator.validate(request);
        OrderEntity orderEntity = OrderMapper.convertToEntity(request);

        orderEntity.setUsername(user);

        OrderEntity entity = this.orderRepository.save(orderEntity);

        return new CreateOrderResponse(entity.getOrderNumber());
    }
}
