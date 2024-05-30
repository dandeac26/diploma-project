package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importRecipes(@RequestParam("file") MultipartFile file) {
        try {
            recipeService.importRecipes(file);
            return new ResponseEntity<>("Recipes imported successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to import recipes", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (HttpMediaTypeNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
