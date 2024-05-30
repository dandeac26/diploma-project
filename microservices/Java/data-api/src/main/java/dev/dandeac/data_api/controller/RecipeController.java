package dev.dandeac.data_api.controller;

import dev.dandeac.data_api.dtos.RecipeDTO;
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
    public ResponseEntity<List<RecipeDTO>> getRecipes() {
        List<RecipeDTO> dtos = recipeService.findRecipes();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<?> getRecipe(@PathVariable String recipeId) {
        try {
            RecipeDTO dto = recipeService.findRecipeById(recipeId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
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

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String recipeId) {
        try {
            recipeService.deleteRecipe(recipeId);
            return new ResponseEntity<>("Recipe with id " + recipeId + " was deleted.", HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<?> updateRecipe(@PathVariable String recipeId,@Valid @RequestBody RecipeDTO recipeDTO) {
        try {
            RecipeDTO dto = recipeService.updateRecipe(recipeId, recipeDTO);
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
