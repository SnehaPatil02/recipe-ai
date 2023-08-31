import { Component, OnInit } from '@angular/core';
import { RecipeAiServiceService } from './recipe-ai-service.service';
import { RecipeInput } from './recipe-input';
import { ImageInput } from './image-input';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public recipeinput: RecipeInput;
  public fullRecipe: string;
  public imageInput: RecipeInput;
  imageToShow : any;
  
  
  ngOnInit() {
    this.recipeinput = Object.assign(new RecipeInput());
  }
  constructor(private recipeService: RecipeAiServiceService) { }
  
  title = 'recipe-app-ui';

  onclick_generate_recipe(){
    console.log(this.recipeinput.token);
    console.log(this.recipeinput.inputText);
    
    this.recipeService.get_Recipe(this.recipeinput).subscribe(result => this.add_fullrecipe(result))
  
  }

  add_fullrecipe(full_r:string){
    this.fullRecipe = full_r;

  }

  generate_Image(){
    console.log("Creating an image")
    this.recipeService.get_Image(this.recipeinput).subscribe(result => this.get_Image(result))
  }

  get_Image(image: Blob){
    let reader = new FileReader();
   reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
   }, false);

   if (image) {
      reader.readAsDataURL(image);
   }
  }
}
