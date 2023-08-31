package com.recipe_app.recipe_app_spring.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import com.recipe_app.recipe_app_spring.entity.ImageInput;
import com.recipe_app.recipe_app_spring.entity.RecipeInput;
import com.theokanning.openai.model.Model;

public interface RecipeAppService {
    public List<Model> getListOfModels(String token);
    public String generateRecipe(RecipeInput inputText);
    public String generateImage(ImageInput imageInput);
    public ResponseEntity<InputStreamResource> imageGenerateSync(RecipeInput recipeInput);
    public ResponseEntity<InputStreamResource> gettingImage(String imageName) throws IOException;
    
}
