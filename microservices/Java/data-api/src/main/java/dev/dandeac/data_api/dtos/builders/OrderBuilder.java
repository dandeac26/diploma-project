package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.OrderDTO;
import dev.dandeac.data_api.dtos.OrderDetailsDTO;
import dev.dandeac.data_api.entity.Order;
import dev.dandeac.data_api.entity.OrderDetails;
import dev.dandeac.data_api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderBuilder {

    private final OrderDetailsBuilder orderDetailsBuilder;

    @Autowired
    private OrderBuilder(OrderDetailsBuilder orderDetailsBuilder) {
        this.orderDetailsBuilder = orderDetailsBuilder;
    }

    public OrderDTO toOrderDTO(Order order) {
        String clientName;
        if(order.getClient().getContactPerson()!=null){
            clientName = order.getClient().getContactPerson();
        }else
            clientName = order.getClient().getFirmName();
        List<OrderDetailsDTO> orderDetailsDTOs = toOrderDetailsDTOList(order.getOrderDetails());
        return new OrderDTO(order.getOrderId(), order.getClientId(), order.getDeliveryNeeded(), order.getCompletionDate(), order.getCompletionTime(), order.getPrice(), clientName, order.getClient().getAddress(), orderDetailsDTOs);
    }


    public static Order toOrder(OrderDTO orderDTO) {
        return new Order(orderDTO.getOrderId(), orderDTO.getClientId(), orderDTO.getDeliveryNeeded(), orderDTO.getCompletionDate(), orderDTO.getCompletionTime(), orderDTO.getPrice());
    }

    private List<OrderDetailsDTO> toOrderDetailsDTOList(List<OrderDetails> orderDetailsList) {
        if(orderDetailsList == null)
            return null;
        return orderDetailsList.stream()
                .map(orderDetailsBuilder::toOrderDetailsDTO)
                .collect(Collectors.toList());
    }
}
