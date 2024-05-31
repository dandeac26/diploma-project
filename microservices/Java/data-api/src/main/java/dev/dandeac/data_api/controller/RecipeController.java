package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.dtos.RecipeDTO;
import dev.dandeac.data_api.entity.RecipeId;
import dev.dandeac.data_api.services.RecipeService;
import dev.dandeac.data_api.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping()
    public ResponseEntity<Map<UUID, List<RecipeDTO>>> getRecipes() {
        List<RecipeDTO> dtos = recipeService.findRecipes();
        Map<UUID, List<RecipeDTO>> groupedDtos = dtos.stream()
                .collect(Collectors.groupingBy(RecipeDTO::getProductId));
        return new ResponseEntity<>(groupedDtos, HttpStatus.OK);
    }

    @GetMapping("/{productId}/{ingredientId}")
    public ResponseEntity<?> getRecipe(@PathVariable String productId, @PathVariable String ingredientId) {
        try {
            RecipeDTO dto = recipeService.findRecipeById(new RecipeId(UUID.fromString(productId), UUID.fromString(ingredientId)));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getRecipesByProductId(@PathVariable String productId) {
        try {
            List<RecipeDTO> dtos = recipeService.findRecipeByProductId(productId);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PostMapping()
    public ResponseEntity<?> addRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        try {
            RecipeDTO dto = recipeService.addRecipe(recipeDTO);
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

    @DeleteMapping("/{productId}/{ingredientId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String productId, @PathVariable String ingredientId) {
        try {
            recipeService.deleteRecipe(productId, ingredientId);
            return new ResponseEntity<>("Recipe of product id  " + productId + " and ingredient id " + ingredientId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductRecipe(@PathVariable String productId) {
        try {
            recipeService.deleteProductRecipe(productId);
            return new ResponseEntity<>("Recipe of product id  " + productId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/{productId}/{ingredientId}")
    public ResponseEntity<?> updateRecipe(@PathVariable String productId, @PathVariable String ingredientId, @Valid @RequestBody RecipeDTO recipeDTO) {
        try {
            RecipeDTO dto = recipeService.updateRecipe(new RecipeId(UUID.fromString(productId),UUID.fromString(ingredientId)), recipeDTO);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return new ResponseEntity<>("All recipes were deleted.", HttpStatus.NO_CONTENT);
    }
}
