package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.OrderDetailsDTO;
import dev.dandeac.data_api.entity.OrderDetails;
import dev.dandeac.data_api.entity.OrderDetailsId;
import dev.dandeac.data_api.entity.Product;
import dev.dandeac.data_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsBuilder {
    private final ProductService productService;

    @Autowired
    public OrderDetailsBuilder(ProductService productService) {
        this.productService = productService;
    }

    public OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails) {

        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setOrderId(orderDetails.getId().getOrderId());
        orderDetailsDTO.setProductId(orderDetails.getId().getProductId());
        orderDetailsDTO.setQuantity(orderDetails.getQuantity());

        Product product = productService.findById(orderDetails.getId().getProductId());
        orderDetailsDTO.setProduct(product);
        return orderDetailsDTO;
    }

    public static OrderDetails toOrderDetails(OrderDetailsDTO orderDetailsDTO) {
        return new OrderDetails(new OrderDetailsId(orderDetailsDTO.getOrderId(), orderDetailsDTO.getProductId()), orderDetailsDTO.getQuantity());
    }
}
