package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.StockDTO;
import dev.dandeac.data_api.entity.Ingredient;
import dev.dandeac.data_api.entity.Provider;
import dev.dandeac.data_api.entity.Stock;
import dev.dandeac.data_api.entity.StockId;
import dev.dandeac.data_api.services.IngredientService;
import dev.dandeac.data_api.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockBuilder {

    private final IngredientService ingredientService;
    private final ProviderService providerService;

    @Autowired
    public StockBuilder(IngredientService ingredientService, ProviderService providerService) {
        this.ingredientService = ingredientService;
        this.providerService = providerService;
    }

    public StockDTO toStockDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setIngredientId(stock.getId().getIngredientId());
        stockDTO.setProviderId(stock.getId().getProviderId());
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setPrice(stock.getPrice());
        stockDTO.setMaxQuantity(stock.getMaxQuantity());
        stockDTO.setQuantityPerPackage(stock.getQuantityPerPackage());

        Ingredient ingredient = ingredientService.findById(stock.getId().getIngredientId());
        Provider provider = providerService.findById(stock.getId().getProviderId());
        stockDTO.setIngredientName(ingredient.getName());
        stockDTO.setProviderName(provider.getName());
        stockDTO.setPackaging(ingredient.getPackaging());

        return stockDTO;
    }

    public Stock toStock(StockDTO stockDTO) {
        return new Stock(new StockId(stockDTO.getIngredientId(),stockDTO.getProviderId()), stockDTO.getQuantity(), stockDTO.getPrice(), stockDTO.getMaxQuantity(), stockDTO.getQuantityPerPackage());
    }
}
