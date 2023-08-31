import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RecipeInput } from './recipe-input';
import { ImageInput } from './image-input';

@Injectable({
  providedIn: 'root'
})
export class RecipeAiServiceService {
  private recipeAIUrl: string;

  constructor(private http: HttpClient) {
    this.recipeAIUrl = 'http://localhost:8080';
  }

  public get_Recipe(recipeInput: RecipeInput) {
      let body = JSON.stringify(recipeInput);
      let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
      return this.http.post(`${this.recipeAIUrl}/generateRecipe`, body, {headers: headers, responseType: 'text'}, 
      );
  }

  public get_Image(imageInput: RecipeInput){
    console.log("Image service")
      let body = JSON.stringify(imageInput);
      let headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post(`${this.recipeAIUrl}/imageGenerateSync`,  body, {headers: headers, responseType: 'blob'})
  }
}
