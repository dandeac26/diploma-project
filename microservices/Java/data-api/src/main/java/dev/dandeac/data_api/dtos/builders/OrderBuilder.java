package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.OrderDTO;
import dev.dandeac.data_api.dtos.OrderDetailsDTO;
import dev.dandeac.data_api.entity.Order;
import dev.dandeac.data_api.services.ClientService;

public class OrderBuilder {

    private OrderBuilder() {
    }


    public static OrderDTO toOrderDTO(Order order) {
        String clientName;
        if(order.getClient().getContactPerson()!=null){
            clientName = order.getClient().getContactPerson();
        }else
            clientName = order.getClient().getFirmName();

        return new OrderDTO(order.getOrderId(), order.getClientId(), order.getDeliveryNeeded(), order.getCompletionDate(), order.getCompletionTime(), order.getPrice(), clientName, order.getClient().getAddress());
    }


    public static Order toOrder(OrderDTO orderDTO) {
        return new Order(orderDTO.getOrderId(), orderDTO.getClientId(), orderDTO.getDeliveryNeeded(), orderDTO.getCompletionDate(), orderDTO.getCompletionTime(), orderDTO.getPrice());
    }
}
