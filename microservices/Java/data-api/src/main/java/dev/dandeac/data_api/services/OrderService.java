package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.ClientDTO;
import dev.dandeac.data_api.dtos.OrderDTO;
import dev.dandeac.data_api.dtos.OrderDetailsDTO;
import dev.dandeac.data_api.dtos.builders.ClientBuilder;
import dev.dandeac.data_api.dtos.builders.OrderBuilder;
import dev.dandeac.data_api.dtos.builders.OrderDetailsBuilder;
import dev.dandeac.data_api.entity.Client;
import dev.dandeac.data_api.entity.Order;
import dev.dandeac.data_api.entity.OrderDetails;
import dev.dandeac.data_api.entity.Product;
import dev.dandeac.data_api.repositories.OrderDetailsRepository;
import dev.dandeac.data_api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ClientService clientService;
    private final OrderDetailsBuilder orderDetailsBuilder;
    private final OrderBuilder orderBuilder;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientService clientService, OrderDetailsRepository orderDetailsRepository, OrderDetailsBuilder orderDetailsBuilder, OrderBuilder orderBuilder, ProductService productService){
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderDetailsBuilder = orderDetailsBuilder;
        this.orderBuilder = orderBuilder;
        this.productService = productService;
    }

    public List<OrderDTO> findOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream()
                .map(orderBuilder::toOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO addOrder(OrderDTO orderDTO) {

        Client client = clientService.findClientEntityById(orderDTO.getClientId().toString());
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client with id " + orderDTO.getClientId() + " does not exist");
        }

        Order order = OrderBuilder.toOrder(orderDTO);
        order.setClient(client);
        order.setPrice(calculateTotalPrice(order));


        Order savedOrder = orderRepository.save(order);
//        if(orderDTO.getOrderDetails() == null)
//            return orderBuilder.toOrderDTO(savedOrder);
//
//        for (OrderDetailsDTO orderDetailsDTO : orderDTO.getOrderDetails()) {
//            Product product = productService
//                    .findById(orderDetailsDTO.getProductId());
//            if (product == null) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with id " + orderDetailsDTO.getProductId() + " does not exist");
//            }
//            addOrderDetails(order.getOrderId().toString(), orderDetailsDTO);
//        }


        return orderBuilder.toOrderDTO(savedOrder);
    }

    private Double calculateTotalPrice(Order order){
        if(order.getOrderDetails() == null)
            return 0.0;
        return order.getOrderDetails().stream()
                .mapToDouble(orderDetails -> orderDetails.getQuantity() * orderDetails.getProduct().getPrice())
                .sum();
    }

    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(UUID.fromString(orderId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " does not exist");
        }
        orderRepository.deleteById(UUID.fromString(orderId));
    }

//    public OrderDTO updateOrder(String orderId, OrderDTO orderDTO) {
//        if (!orderRepository.existsById(UUID.fromString(orderId))) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " does not exist");
//        }
//
//        Order order = OrderBuilder.toOrder(orderDTO);
//        order.setOrderId(UUID.fromString(orderId));
//        order.setPrice(calculateTotalPrice(order));
//        Order updatedOrder = orderRepository.save(order);
//        return orderBuilder.toOrderDTO(updatedOrder);
//    }
    public OrderDTO updateOrder(String orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " does not exist"));

        // Update the fields of the existing order instead of replacing the order
        existingOrder.setClientId(orderDTO.getClientId());
        existingOrder.setDeliveryNeeded(orderDTO.getDeliveryNeeded());
        existingOrder.setCompletionDate(orderDTO.getCompletionDate());
        existingOrder.setCompletionTime(orderDTO.getCompletionTime());

        existingOrder.setPrice(calculateTotalPrice(existingOrder));
        existingOrder.setCompleted(orderDTO.getCompleted());
        Order updatedOrder = orderRepository.save(existingOrder);
        return orderBuilder.toOrderDTO(updatedOrder);
    }

    public OrderDTO findOrderById(String orderId) {
        Order order = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " does not exist"));
        return orderBuilder.toOrderDTO(order);
    }

    public Order findOrderEntityById(String orderId) {
        return orderRepository.findById(UUID.fromString(orderId))
                .orElse(null);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    /// ORDER DETAILS LOGIC

    public OrderDetailsDTO addOrderDetails(String orderId, OrderDetailsDTO orderDetailsDTO) {
        Order order = findOrderEntityById(orderId);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with id " + orderId + " does not exist");
        }

        OrderDetails orderDetails = OrderDetailsBuilder.toOrderDetails(orderDetailsDTO);
        orderDetails.setOrder(order);

        Product product = productService.findById(orderDetailsDTO.getProductId());
        orderDetails.setProduct(product);

        OrderDetails savedOrderDetails = orderDetailsRepository.save(orderDetails);

        order.setPrice(calculateTotalPrice(order));
        orderRepository.save(order);
        return orderDetailsBuilder.toOrderDetailsDTO(savedOrderDetails);
    }

    public List<OrderDetailsDTO> findOrderDetailsByOrderId(String orderId) {
        Order order = findOrderEntityById(orderId);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with id " + orderId + " does not exist");
        }

        List<OrderDetails> orderDetailsList = orderDetailsRepository.findByIdOrderId(order.getOrderId());
        if (orderDetailsList == null) {
            return new ArrayList<>();
        } else {
            return orderDetailsList.stream()
                    .map(orderDetailsBuilder::toOrderDetailsDTO)
                    .collect(Collectors.toList());
        }
    }

    public void deleteOrderDetails(String orderId, String productId) {
        Order order = findOrderEntityById(orderId);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with id " + orderId + " does not exist");
        }

        OrderDetails orderDetails = orderDetailsRepository.findByIdOrderIdAndIdProductId(order.getOrderId(), UUID.fromString(productId));
        if (orderDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OrderDetails with orderId " + orderId + " and productId " + productId + " does not exist");
        }

        orderDetailsRepository.delete(orderDetails);

        order.setPrice(calculateTotalPrice(order));
        orderRepository.save(order);
    }

//    public List<OrderDTO> findOrdersByCompletionDate(String completionDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Adjust this format to match your date string
//        Date date = null;
//        try {
//            date = new Date(formatter.parse(completionDate).getTime());
//        } catch (ParseException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
//        }
//        List<Order> orderList = orderRepository.findOrdersByCompletionDate(date);
//        return orderList.stream()
//                .map(orderBuilder::toOrderDTO)
//                .collect(Collectors.toList());
//    }

    public List<OrderDTO> findOrdersByCompletionDate(String completionDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Adjust this format to match your date string
        Date date = null;
        try {
            date = new Date(formatter.parse(completionDate).getTime());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }
        List<Order> orderList = orderRepository.findOrdersByCompletionDate(date);
        return orderList.stream()
                .sorted(Comparator.comparing(Order::getCompletionTime))
                .map(orderBuilder::toOrderDTO)
                .collect(Collectors.toList());
    }
}
