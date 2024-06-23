package dev.dandeac.data_api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class OrderDTO {
    private UUID orderId;

    @NotNull(message = "clientId cannot be null")
    private UUID clientId;

    @NotNull(message = "deliveryNeeded cannot be null")
    private Boolean deliveryNeeded;

    @NotNull(message = "completionDate cannot be null")
    private Date completionDate;

    private Time completionTime;

    @PositiveOrZero(message = "price must be positive or zero")
    private Double price;

    private Boolean completed;

    private String clientName;

    private String clientLocation;

    private String clientPhoneNumber;

    private List<OrderDetailsDTO> orderDetails;

    public OrderDTO(){}

    public OrderDTO(UUID orderId, UUID clientId, Boolean deliveryNeeded, Date completionDate, Time completionTime, Double price, Boolean completed) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.deliveryNeeded = deliveryNeeded;
        this.completionDate = completionDate;
        this.completionTime = completionTime;
        this.price = price;
        this.completed = completed;
    }

    public OrderDTO(UUID orderId, UUID clientId, Boolean deliveryNeeded, Date completionDate, Time completionTime, Double price, Boolean completed, String clientName, String clientLocation) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.deliveryNeeded = deliveryNeeded;
        this.completionDate = completionDate;
        this.completionTime = completionTime;
        this.price = price;
        this.completed = completed;
        this.clientName = clientName;
        this.clientLocation = clientLocation;
    }

    public OrderDTO(UUID orderId, UUID clientId, Boolean deliveryNeeded, Date completionDate, Time completionTime, Double price, Boolean completed, String clientName, String clientLocation, String clientPhoneNumber, List<OrderDetailsDTO> orderDetails) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.deliveryNeeded = deliveryNeeded;
        this.completionDate = completionDate;
        this.completionTime = completionTime;
        this.price = price;
        this.completed = completed;
        this.clientName = clientName;
        this.clientLocation = clientLocation;
        this.clientPhoneNumber = clientPhoneNumber;
        this.orderDetails = orderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO order = (OrderDTO) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(clientId, order.clientId) && Objects.equals(deliveryNeeded, order.deliveryNeeded) && Objects.equals(completionDate, order.completionDate) && Objects.equals(completionTime, order.completionTime) && Objects.equals(price, order.price) && Objects.equals(completed, order.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, deliveryNeeded, completionDate, completionTime, price, completed);
    }
}
