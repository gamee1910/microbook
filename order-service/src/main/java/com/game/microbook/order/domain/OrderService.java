package com.game.microbook.order.domain;

import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.domain.model.CreateOrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(String user, CreateOrderRequest request) {
        OrderEntity orderEntity = OrderMapper.convertToEntity(request);

        orderEntity.setUsername(user);

        OrderEntity entity = this.orderRepository.save(orderEntity);

        return new CreateOrderResponse(entity.getOrderNumber());
    }
}
