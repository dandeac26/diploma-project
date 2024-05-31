package dev.dandeac.data_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dandeac.data_api.dtos.RecipeDTO;
import dev.dandeac.data_api.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeService recipeService;

    private UUID productId;
    private UUID ingredientId;
    private RecipeDTO recipeDTO;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        ingredientId = UUID.randomUUID();
        recipeDTO = new RecipeDTO();
        recipeDTO.setProductId(productId);
        recipeDTO.setIngredientId(ingredientId);
        recipeDTO.setQuantity(100.0);
    }

    @Test
    void testCreateRecipe() throws Exception {
        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.ingredientId").value(ingredientId.toString()))
                .andExpect(jsonPath("$.quantity").value(recipeDTO.getQuantity()));
    }

    @Test
    void testGetRecipe() throws Exception {
        RecipeDTO savedRecipe = recipeService.addRecipe(recipeDTO);

        mockMvc.perform(get("/recipe/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(productId.toString()))
                .andExpect(jsonPath("$[0].ingredientId").value(ingredientId.toString()))
                .andExpect(jsonPath("$[0].quantity").value(recipeDTO.getQuantity()));
    }

    @Test
    void testUpdateRecipe() throws Exception {
        RecipeDTO savedRecipe = recipeService.addRecipe(recipeDTO);
        RecipeDTO updatedRecipeDTO = new RecipeDTO();
        updatedRecipeDTO.setProductId(productId);
        updatedRecipeDTO.setIngredientId(ingredientId);
        updatedRecipeDTO.setQuantity(200.0);

        mockMvc.perform(put("/recipe/" + productId + "/" + ingredientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.ingredientId").value(ingredientId.toString()))
                .andExpect(jsonPath("$.quantity").value(updatedRecipeDTO.getQuantity()));
    }

    @Test
    void testDeleteRecipe() throws Exception {
        RecipeDTO savedRecipe = recipeService.addRecipe(recipeDTO);

        // Check that the recipe exists after it is saved
        mockMvc.perform(get("/recipe/" + productId + "/" + ingredientId))
                .andExpect(status().isOk());

        // Delete the recipe
        mockMvc.perform(delete("/recipe/" + productId + "/" + ingredientId))
                .andExpect(status().isNoContent());
    }
}
