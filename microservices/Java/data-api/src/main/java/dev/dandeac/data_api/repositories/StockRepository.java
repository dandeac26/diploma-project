package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Stock;
import dev.dandeac.data_api.entity.Stock;
import dev.dandeac.data_api.entity.StockId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface StockRepository extends JpaRepository<Stock, StockId>{
    boolean existsByIdProviderIdAndIdIngredientId(UUID ingredientId, UUID productId);
    Stock findByIdProviderIdAndIdIngredientId(UUID ingredientId, UUID productId);
    List<Stock> findByIdIngredientId(UUID productId);
}
