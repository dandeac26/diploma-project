package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "stocks_tb")
public class Stock {

    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "stocks_id")
    private StockId stockId;

    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "price")
    private Double price;

    public Stock(){}

    public Stock(UUID ingredientId, UUID providerId, Integer quantity, Double price) {
        this.stockId = new StockId(ingredientId, providerId);
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(stockId, stock.stockId) && Objects.equals(quantity, stock.quantity) && Objects.equals(price, stock.price) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, quantity, price);
    }
}
