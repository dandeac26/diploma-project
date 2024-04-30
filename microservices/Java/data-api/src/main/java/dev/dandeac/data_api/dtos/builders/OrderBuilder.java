package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.OrderDTO;
import dev.dandeac.data_api.dtos.OrderDetailsDTO;
import dev.dandeac.data_api.entity.Order;

public class OrderBuilder {
    private OrderBuilder() {
    }

    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(order.getOrderId(), order.getClientId(), order.getLocation(), order.getDeliveryDate(), order.getDeliveryTime(), order.getPrice());
    }

    public static Order toEntity(OrderDetailsDTO orderDetailsDTO) {
        return new Order(orderDetailsDTO.getLocation(), orderDetailsDTO.getDeliveryDate(), orderDetailsDTO.getDeliveryTime(), orderDetailsDTO.getPrice());
    }
}
