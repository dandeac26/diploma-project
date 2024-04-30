package dev.dandeac.data_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDetailsDTO {
    private UUID productId;

    private String name;

    private Double price;

    public ProductDetailsDTO(){}

    public ProductDetailsDTO(UUID productId, String name, Double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public ProductDetailsDTO(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
