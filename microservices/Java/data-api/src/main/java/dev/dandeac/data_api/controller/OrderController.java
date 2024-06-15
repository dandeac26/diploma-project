package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.dtos.OrderDTO;
import dev.dandeac.data_api.dtos.OrderDetailsDTO;
import dev.dandeac.data_api.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    
    
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> dtos = orderService.findOrders();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/byDate/{completionDate}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCompletionDate(@PathVariable String completionDate) {
        List<OrderDTO> dtos = orderService.findOrdersByCompletionDate(completionDate);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        try {
            OrderDTO dto = orderService.findOrderById(orderId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PostMapping()
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO dto = orderService.addOrder(orderDTO);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>("Order with id " + orderId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable String orderId,@Valid @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO dto = orderService.updateOrder(orderId, orderDTO);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllOrders() {
        orderService.deleteAllOrders();
        return new ResponseEntity<>("All orders were deleted.", HttpStatus.NO_CONTENT);
    }
    
    
    /// ORDER DETAILS ENDPOINTS

    @PostMapping("/{orderId}/details")
    public ResponseEntity<?> addOrderDetails(@PathVariable String orderId, @Valid @RequestBody OrderDetailsDTO orderDetailsDTO) {
        try {
            if(orderDetailsDTO.getOrderId() == null)
                orderDetailsDTO.setOrderId(UUID.fromString(orderId));
            OrderDetailsDTO dto = orderService.addOrderDetails(orderId, orderDetailsDTO);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId) {
        try {
            List<OrderDetailsDTO> dtos = orderService.findOrderDetailsByOrderId(orderId);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("/{orderId}/details/{productId}")
    public ResponseEntity<String> deleteOrderDetails(@PathVariable String orderId, @PathVariable String productId) {
        try {
            orderService.deleteOrderDetails(orderId, productId);
            return new ResponseEntity<>("OrderDetails with orderId " + orderId + " and productId " + productId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
