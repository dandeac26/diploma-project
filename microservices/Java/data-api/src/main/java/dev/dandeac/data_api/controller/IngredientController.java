package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importIngredients(@RequestParam("file") MultipartFile file) {
        try {
            ingredientService.importIngredients(file);
            return new ResponseEntity<>("Ingredients imported successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to import ingredients", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}