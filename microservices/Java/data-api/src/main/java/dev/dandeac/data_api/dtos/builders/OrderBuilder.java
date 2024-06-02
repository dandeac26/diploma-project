package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.OrderDTO;
import dev.dandeac.data_api.dtos.OrderDetailsDTO;
import dev.dandeac.data_api.entity.Order;

public class OrderBuilder {
    private OrderBuilder() {
    }

    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(order.getOrderId(), order.getClientId(), order.getDeliveryNeeded(), order.getCompletionDate(), order.getCompletionTime(), order.getPrice());
    }


    public static Order toOrder(OrderDTO orderDTO) {
        return new Order(orderDTO.getOrderId(), orderDTO.getClientId(), orderDTO.getDeliveryNeeded(), orderDTO.getCompletionDate(), orderDTO.getCompletionTime(), orderDTO.getPrice());
    }
}
