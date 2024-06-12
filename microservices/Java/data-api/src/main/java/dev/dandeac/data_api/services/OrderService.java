package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.ClientDTO;
import dev.dandeac.data_api.dtos.OrderDTO;
import dev.dandeac.data_api.dtos.builders.ClientBuilder;
import dev.dandeac.data_api.dtos.builders.OrderBuilder;
import dev.dandeac.data_api.entity.Client;
import dev.dandeac.data_api.entity.Order;
import dev.dandeac.data_api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    @Autowired
    public OrderService(OrderRepository orderRepository, ClientService clientService){
        this.orderRepository = orderRepository;
        this.clientService = clientService;
    }

    public List<OrderDTO> findOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream()
                .map(OrderBuilder::toOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO addOrder(OrderDTO orderDTO) {

        Client client = clientService.findClientEntityById(orderDTO.getClientId().toString());
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client with id " + orderDTO.getClientId() + " does not exist");
        }

        Order order = OrderBuilder.toOrder(orderDTO);
        order.setClient(client);

        Order savedOrder = orderRepository.save(order);
        return OrderBuilder.toOrderDTO(savedOrder);
    }

    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(UUID.fromString(orderId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " does not exist");
        }
        orderRepository.deleteById(UUID.fromString(orderId));
    }

    public OrderDTO updateOrder(String orderId, OrderDTO orderDTO) {
        if (!orderRepository.existsById(UUID.fromString(orderId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " does not exist");
        }

//        if (orderRepository.existsByFirmName(orderDTO.getFirmName()) && !orderRepository.findByFirmName(orderDTO.getFirmName()).getOrderId().equals(UUID.fromString(orderId) )){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with name " + orderDTO.getFirmName() + " already exists");
//        }
        Order order = OrderBuilder.toOrder(orderDTO);
        order.setOrderId(UUID.fromString(orderId));
        Order updatedOrder = orderRepository.save(order);
        return OrderBuilder.toOrderDTO(updatedOrder);
    }

    public OrderDTO findOrderById(String orderId) {
        Order order = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " does not exist"));
        return OrderBuilder.toOrderDTO(order);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
