# recipe-ai
 
Welcome to the Recipe AI Single page application repository! 
 This project combines the power of Angular for the front-end and Spring Boot for the backend to create a Recipe AI that suggests recipes and thier images using the OpenAI API.

#### Technologies:
- OpenAI API
- Spring Boot
- Angular

#### Maven Dependency for OpenAi API:
```xml
   <dependency>
    <groupId>com.theokanning.openai-gpt3-java</groupId>
    <artifactId>{api|client|service}</artifactId>
    <version>version</version>       
   </dependency>
```


#### OpenAI API Key:
openai.api-key=YOUR_OPENAI_API_KEY

## Key Components:
- Front-end Interface: The project likely includes a user-friendly interface where users can interact with the AI.
- OpenAI API Integration: The core functionality of the project lies in integrating the OpenAI API. The API is used to process user queries,
 understand context, and generate recipes in response with the image of outcome. The API enables the AI to generate coherent and contextually relevant text.
- BackEnd: Backend server handles the user requests, interact with the OpenAI API, and manage data flow. This is implemented using SpringBoot.

#### How it works:
- For this you need your OpenAI API key and enter your key in token field.
- Enter your recipe-related queries in the provided input field. The AI will respond with recipe and will also generate image of the outcome using the OpenAI API.

### Screenshot

![RecipeAI Screenshot](https://github.com/SnehaPatil02/recipe-ai/blob/main/RecipeAI%20Sceernshot.png "RecipeAI Screenshot")
 
