package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.IngredientDTO;
import dev.dandeac.data_api.dtos.builders.IngredientBuilder;
import dev.dandeac.data_api.entity.Ingredient;
import dev.dandeac.data_api.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientDTO> findIngredients() {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        return ingredientList.stream()
                .map(IngredientBuilder::toIngredientDTO)
                .collect(Collectors.toList());
    }

    public IngredientDTO addIngredient(IngredientDTO ingredientDTO) {

        if (ingredientRepository.existsByName(ingredientDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient with name " + ingredientDTO.getName() + " already exists");
        }
        Ingredient ingredient = IngredientBuilder.toIngredient(ingredientDTO);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientBuilder.toIngredientDTO(savedIngredient);
    }

    public void deleteIngredient(String ingredientId) {
        if (!ingredientRepository.existsById(UUID.fromString(ingredientId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id " + ingredientId + " does not exist");
        }
        ingredientRepository.deleteById(UUID.fromString(ingredientId));
    }

    public IngredientDTO updateIngredient(String ingredientId, IngredientDTO ingredientDTO) {
        if (!ingredientRepository.existsById(UUID.fromString(ingredientId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id " + ingredientId + " does not exist");
        }

        if (ingredientRepository.existsByName(ingredientDTO.getName()) && !ingredientRepository.findByName(ingredientDTO.getName()).getIngredientId().equals(UUID.fromString(ingredientId) )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingredient with name " + ingredientDTO.getName() + " already exists");
        }
        Ingredient ingredient = IngredientBuilder.toIngredient(ingredientDTO);
        ingredient.setIngredientId(UUID.fromString(ingredientId));
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        return IngredientBuilder.toIngredientDTO(updatedIngredient);
    }

    public IngredientDTO findIngredientById(String ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(UUID.fromString(ingredientId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id " + ingredientId + " does not exist"));
        return IngredientBuilder.toIngredientDTO(ingredient);
    }

    public void deleteAllIngredients() {
        ingredientRepository.deleteAll();
    }
}