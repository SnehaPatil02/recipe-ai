package com.recipe_app.recipe_app_spring.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class RecipeFinder {
     private static OpenAiService service;

    public RecipeFinder(String token) {
        service = new OpenAiService(token);
    }

    public String[] isRecipe(String input) {
        // print where input is a receipe or not
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), 
        "You are a recipe sharing bot which tells if text is a recipe or not in a json format:\n"+ 
        "{'isrecipe': <boolean>,\n'recipename': <string>, \n 'dishdescription' : <string> //detailed description of dish after preparing it using the recipe being eaten by a fat man \\n" + //
                " } "));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), input));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                        .builder()
                        .model("gpt-3.5-turbo")
                        .messages(messages)
                        .n(1)
                        .maxTokens(300)
                        .logitBias(new HashMap<>())
                        .build();
      
        ChatCompletionResult result = service.createChatCompletion(chatCompletionRequest);
      
        String content = result.getChoices().get(0).getMessage().getContent();
        content = content.replaceAll("'", "\"");
        content = content.replace("True", "true");
        content = content.replace("False", "false");

        System.out.println("------------------");
        System.out.println(content);
       
       System.out.println("------------------");
        System.out.println(content);  
        ObjectMapper objectMapper = new ObjectMapper();
        try{
        RecipeResponse r = objectMapper.readValue(content, RecipeResponse.class);
    if(r.getIsrecipe() == true){
            System.out.println(r.getDishdescription());
            return new String[] {r.getRecipename(), r.getDishdescription()};
          
        }
        else{
            return new String[] {};
        }    
    
    }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error");
            return new String[] {};
        
        }
        
    }  
}
