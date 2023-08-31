package com.recipe_app.recipe_app_spring.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.recipe_app.recipe_app_spring.entity.ImageInput;
import com.recipe_app.recipe_app_spring.entity.ModelListInput;
import com.recipe_app.recipe_app_spring.entity.RecipeInput;
import com.recipe_app.recipe_app_spring.service.RecipeAppService;
import com.theokanning.openai.model.Model;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RecipeAppController {
    

    @Autowired
    private RecipeAppService recipeAppService;
    
    @PostMapping("/listModels")
    public List<Model> getModel(@RequestBody ModelListInput input){
        //System.out.println(input.getToken());
        return this.recipeAppService.getListOfModels(input.getToken());
    }

    @PostMapping("/generateRecipe")
    public String generateRecipe(@RequestBody RecipeInput recipeInput){
        System.out.println(recipeInput.getToken());
        System.out.println(recipeInput.getInputText());
        
        String result = recipeAppService.generateRecipe(recipeInput);
        
        return result;   
    }

    @PostMapping("/generateImage")
    public String generateImage(@RequestBody ImageInput imageInput){

    // 1.   get recipe name from recipe
    // 2. generate image and save locally
    // 3. return image name
        String imageName = recipeAppService.generateImage(imageInput);

        return imageName;
    }

    @PostMapping("/imageGenerateSync")
    public ResponseEntity<InputStreamResource> imageGenerateSync(@RequestBody RecipeInput recipeInput){

        return recipeAppService.imageGenerateSync(recipeInput);
    }

    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<InputStreamResource> gettingImage(@PathVariable String imageName) throws IOException{
        // return saved image
        return recipeAppService.gettingImage(imageName);
      
    }

}

