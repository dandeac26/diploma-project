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
    private StockId id;

    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "price")
    private Double price;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    public Stock(){}

    public Stock(StockId id, Integer quantity, Double price, Integer maxQuantity) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.maxQuantity = maxQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) && Objects.equals(quantity, stock.quantity) && Objects.equals(price, stock.price) && Objects.equals(maxQuantity, stock.maxQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, maxQuantity);
    }
}
