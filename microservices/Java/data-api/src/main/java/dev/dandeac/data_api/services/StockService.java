package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.StockDTO;
import dev.dandeac.data_api.dtos.builders.StockBuilder;
import dev.dandeac.data_api.entity.Stock;
import dev.dandeac.data_api.entity.StockId;
import dev.dandeac.data_api.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public List<StockDTO> findStocks() {
        List<Stock> stockList = stockRepository.findAll();
        return stockList.stream()
                .map(StockBuilder::toStockDTO)
                .collect(Collectors.toList());
    }

    public StockDTO addStock(StockDTO stockDTO) {

        if (stockRepository.existsByIdProviderIdAndIdIngredientId(stockDTO.getProviderId(), stockDTO.getIngredientId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock for product " + stockDTO.getIngredientId() + " already exists");
        }
        Stock stock = StockBuilder.toStock(stockDTO);
        Stock savedStock = stockRepository.save(stock);
        return StockBuilder.toStockDTO(savedStock);
    }

    public void deleteStock(String ingredientId, String providerId) {
        StockId id = new StockId(UUID.fromString(ingredientId), UUID.fromString(providerId));
        if (!stockRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock with product id " + ingredientId + " and ingredient id "+ providerId + " does not exist");
        }
        stockRepository.deleteById(id);
    }

    public void deleteProductStock(String ingredientId) {
        List<Stock> stocks = stockRepository.findByIdIngredientId(UUID.fromString(ingredientId));
        if (stocks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stocks with product id " + ingredientId + " do not exist");
        }
        stockRepository.deleteAll(stocks);
    }

    public StockDTO updateStock(StockId stockId, StockDTO stockDTO) {
        if (!stockRepository.existsById(stockId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock with id " + stockId + " does not exist");
        }

        Stock stock = StockBuilder.toStock(stockDTO);
        stock.setId(stockId);
        Stock updatedStock = stockRepository.save(stock);
        return StockBuilder.toStockDTO(updatedStock);
    }

    public List<StockDTO> findStockByIngredientId(String ingredientId) {
        List<Stock> stocks = stockRepository.findByIdIngredientId(UUID.fromString(ingredientId));
        return stocks.stream()
                .map(StockBuilder::toStockDTO)
                .collect(Collectors.toList());
    }

    public void deleteAllStocks() {
        stockRepository.deleteAll();
    }
    
    public StockDTO findStockById(StockId stockId) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock with id " + stockId + " does not exist"));
        return StockBuilder.toStockDTO(stock);
    }
}
