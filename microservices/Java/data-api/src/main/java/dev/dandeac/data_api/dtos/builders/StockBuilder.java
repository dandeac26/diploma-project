package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.StockDTO;
import dev.dandeac.data_api.entity.Stock;
import dev.dandeac.data_api.entity.StockId;

public class StockBuilder {
    private StockBuilder() {
    }

    public static StockDTO toStockDTO(Stock stock) {
        return new StockDTO(
                stock.getId().getIngredientId(),
                stock.getId().getProviderId(),
                stock.getQuantity(),
                stock.getPrice()
        );
    }

    public static Stock toStock(StockDTO stockDTO) {
        StockId id = new StockId(stockDTO.getIngredientId(), stockDTO.getProviderId());
        return new Stock(id, stockDTO.getQuantity(), stockDTO.getPrice());
    }
}
