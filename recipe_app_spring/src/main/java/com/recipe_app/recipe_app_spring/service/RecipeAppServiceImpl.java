package com.recipe_app.recipe_app_spring.service;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.recipe_app.recipe_app_spring.entity.ImageInput;
import com.recipe_app.recipe_app_spring.entity.RecipeFinder;
import com.recipe_app.recipe_app_spring.entity.RecipeInput;

import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.model.Model;
import com.theokanning.openai.service.OpenAiService;


@Service
public class RecipeAppServiceImpl implements RecipeAppService {
   
    private OpenAiService service;
    private List<ChatMessage> messages = new ArrayList<>();


    @Override
    public List<Model> getListOfModels(String token) {
        this.service = new OpenAiService(token);
        
        System.out.println("Streaming chat with ChatGPT...");
        
         return service.listModels();

    }

    @Override
    public String generateRecipe(RecipeInput recipeInput) {
        this.service = new OpenAiService(recipeInput.getToken());
        System.out.println("Streaming chat with ChatGPT...");
        ChatMessage formatted_input = new ChatMessage(ChatMessageRole.USER.value() , "You are a recipe bot and will give only correct recipe for the input you get from the user" + recipeInput.getInputText());
       
        this.messages.add(formatted_input);


        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(500)
                .logitBias(new HashMap<>())
                .build();

        
         ChatMessage temp  = new ChatMessage(ChatMessageRole.ASSISTANT.value(), "");
    
        service.streamChatCompletion(chatCompletionRequest)
        .doOnError(Throwable::printStackTrace)
        .map(ChatCompletionChunk::getChoices)
        .map(choices -> choices.get(0).getMessage())
        .map(message -> {if(message.getContent()==null){
                return "";
        }
        else{
                
                return message.getContent();
        }}
        )
        .blockingIterable().forEach(item ->{
            temp.setContent(temp.getContent() + item);
            System.out.print(item);
        }
        );

        messages.add(temp);
        return temp.getContent();
       
    }

    @Override
    public String generateImage(ImageInput imageInput) {
        this.service = new OpenAiService(imageInput.getToken());

        RecipeFinder rf = new RecipeFinder(imageInput.getToken());
        String[] isRecipe = rf.isRecipe(imageInput.getRecipe());

        CreateImageRequest request = CreateImageRequest.builder()
        .prompt("A clear picture of " + isRecipe[0] ) 
        .size("256x256")
        .responseFormat("b64_json")   
        .build();

        ImageResult image_result = service.createImage(request);
        System.out.println("Image is created successfully! ");

        byte[] decodedImg = Base64.getDecoder()
                .decode(image_result.getData().get(0).getB64Json().getBytes());
                Path destinationFile = Paths.get("C:\\MachineLearningProjects\\recipe_app_spring\\src\\main\\java\\com\\recipe_app\\recipe_app_spring", isRecipe[0] + ".png");
              try {
                Files.write(destinationFile, decodedImg);
              } catch (IOException e) {
                e.printStackTrace();
              }

        return isRecipe[0];
      
       
    }

    @Override
    public ResponseEntity<InputStreamResource> gettingImage(String imageName) throws IOException{

       File file = new File("C:\\MachineLearningProjects\\recipe_app_spring\\src\\main\\java\\com\\recipe_app\\recipe_app_spring\\" + imageName);
        InputStream targetStream = new FileInputStream(file);
 
       return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(targetStream));
        
    }

    @Override
    public ResponseEntity<InputStreamResource> imageGenerateSync(RecipeInput recipeInput) {
      this.service = new OpenAiService(recipeInput.getToken());
      ImageInput ii = new ImageInput();
      CreateImageRequest request = CreateImageRequest.builder()
        .prompt("you will only give correct as given the recipename and as the recipe, clear like photoshoot and beatiful picture like 5 star restaurant and not closeup and not random picture of "+ recipeInput.getInputText()) 
        .size("256x256")
        .responseFormat("b64_json")   
        .build();

         ImageResult image_result = service.createImage(request);
        System.out.println("Image is created successfully! ");

        byte[] decodedImg = Base64.getDecoder()
                .decode(image_result.getData().get(0).getB64Json().getBytes());
        
        InputStream is = new ByteArrayInputStream(decodedImg);

      return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));
    }
  
    
}
