package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);

    Product findByName(String name);

    boolean existsByProductId(UUID productId);
}
