package dev.dandeac.data_api.services;

import au.com.bytecode.opencsv.CSVReader;
import dev.dandeac.data_api.entity.Recipe;
import dev.dandeac.data_api.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void importRecipes(MultipartFile file) throws IOException, HttpMediaTypeNotSupportedException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".csv")) {
            throw new HttpMediaTypeNotSupportedException("Invalid file type. Please upload a CSV file.");
        }

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReader(reader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length >= 3) {
                    Recipe recipe = new Recipe(
                            UUID.fromString(String.valueOf(nextRecord[0])),
                            UUID.fromString(String.valueOf(nextRecord[1])),
                            Double.parseDouble(String.valueOf(nextRecord[2]))
                    );
                    recipeRepository.save(recipe);
                } else {
                    // handle the case when nextRecord has less than 3 elements
                }
            }
        }
    }
}
