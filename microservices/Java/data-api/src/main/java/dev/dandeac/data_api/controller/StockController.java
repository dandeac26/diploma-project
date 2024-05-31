package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.dtos.StockDTO;
import dev.dandeac.data_api.entity.StockId;
import dev.dandeac.data_api.services.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/stock")
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @GetMapping()
    public ResponseEntity<Map<UUID, List<StockDTO>>> getStocks() {
        List<StockDTO> dtos = stockService.findStocks();
        Map<UUID, List<StockDTO>> groupedDtos = dtos.stream()
                .collect(Collectors.groupingBy(StockDTO::getIngredientId));
        return new ResponseEntity<>(groupedDtos, HttpStatus.OK);
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity<?> getStocksByIngredientId(@PathVariable String ingredientId) {
        try {
            List<StockDTO> dtos = stockService.findStockByIngredientId(ingredientId);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @GetMapping("/{ingredientId}/{providerId}")
    public ResponseEntity<?> getStock(@PathVariable String ingredientId, @PathVariable String providerId) {
        try {
            StockDTO dto = stockService.findStockById(new StockId(UUID.fromString(ingredientId), UUID.fromString(providerId)));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PostMapping()
    public ResponseEntity<?> addStock(@Valid @RequestBody StockDTO stockDTO) {
        try {
            StockDTO dto = stockService.addStock(stockDTO);
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

    @DeleteMapping("/{ingredientId}/{providerId}")
    public ResponseEntity<String> deleteStock(@PathVariable String ingredientId, @PathVariable String providerId) {
        try {
            stockService.deleteStock(ingredientId, providerId);
            return new ResponseEntity<>("Stock of product id  " + ingredientId + " and ingredient id " + providerId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<String> deleteProductStock(@PathVariable String ingredientId) {
        try {
            stockService.deleteProductStock(ingredientId);
            return new ResponseEntity<>("Stock of product id  " + ingredientId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/{ingredientId}/{providerId}")
    public ResponseEntity<?> updateStock(@PathVariable String ingredientId, @PathVariable String providerId, @Valid @RequestBody StockDTO stockDTO) {
        try {
            StockDTO dto = stockService.updateStock(new StockId(UUID.fromString(ingredientId),UUID.fromString(providerId)), stockDTO);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllStocks() {
        stockService.deleteAllStocks();
        return new ResponseEntity<>("All stocks were deleted.", HttpStatus.NO_CONTENT);
    }
}