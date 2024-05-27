package dev.dandeac.data_api.services;

import au.com.bytecode.opencsv.CSVReader;
import dev.dandeac.data_api.entity.Ingredient;
import dev.dandeac.data_api.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.UUID;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public void importIngredients(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReader(reader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length >= 3) {
                    Ingredient ingredient = new Ingredient(
                            String.valueOf(nextRecord[0]),
                            String.valueOf(nextRecord[1]),
                           Double.parseDouble(String.valueOf(nextRecord[2]))
                    );
                    ingredientRepository.save(ingredient);
                } else {
                    // handle the case when nextRecord has less than 3 elements
                }
            }



        }
    }
}