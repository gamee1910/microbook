package com.game.microbook.order.domain;

import com.game.microbook.order.domain.model.CreateOrderRequest;
import com.game.microbook.order.domain.model.OrderDTO;
import com.game.microbook.order.domain.model.OrderItem;
import com.game.microbook.order.domain.model.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderMapper {

    static OrderEntity convertToEntity(CreateOrderRequest request) {
        var newOrder = new OrderEntity();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(request.customer());
        newOrder.setDeliveryAddress(request.deliveryAddress());
        Set<OrderItemEntity> orderItems = new HashSet<>();
        for (OrderItem item : request.items()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCode(item.code());
            orderItem.setName(item.name());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }
        newOrder.setItems(orderItems);
        return newOrder;
    }

    static OrderDTO convertToDTO(OrderEntity order) {
        var orderItems = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDTO(
                order.getOrderNumber(),
                order.getUsername(),
                orderItems,
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getComments(),
                order.getCreatedAt());
    }
}
