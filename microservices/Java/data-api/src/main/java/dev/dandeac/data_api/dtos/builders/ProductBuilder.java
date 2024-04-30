package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.ProductDTO;
import dev.dandeac.data_api.dtos.ProductDetailsDTO;
import dev.dandeac.data_api.entity.Product;

public class ProductBuilder {
    private ProductBuilder() {
    }

    public static ProductDTO toProductDTO(Product product) {
        return new ProductDTO(product.getProductId(), product.getName(), product.getPrice());
    }

    public static Product toEntity(ProductDetailsDTO productDetailsDTO) {
        return new Product(productDetailsDTO.getName(),
                productDetailsDTO.getPrice()
                );
    }
}
