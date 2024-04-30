package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.ProductDTO;
import dev.dandeac.data_api.dtos.builders.ProductBuilder;
import dev.dandeac.data_api.entity.Product;
import dev.dandeac.data_api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(ProductBuilder::toProductDTO)
                .collect(Collectors.toList());
    }
}
