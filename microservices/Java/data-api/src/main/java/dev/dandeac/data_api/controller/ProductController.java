package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.dtos.ProductDTO;
import dev.dandeac.data_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

//    @CrossOrigin
    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> dtos = productService.findProducts();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
