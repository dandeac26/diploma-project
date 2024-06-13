package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.OrderDetails;
import dev.dandeac.data_api.entity.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsId> {
    boolean existsByIdOrderIdAndIdProductId(UUID orderId, UUID productId);
    OrderDetails findByIdOrderIdAndIdProductId(UUID orderId, UUID productId);
    List<OrderDetails> findByIdOrderId(UUID orderId);
}
