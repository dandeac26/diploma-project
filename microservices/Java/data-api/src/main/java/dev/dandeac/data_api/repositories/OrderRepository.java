package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
