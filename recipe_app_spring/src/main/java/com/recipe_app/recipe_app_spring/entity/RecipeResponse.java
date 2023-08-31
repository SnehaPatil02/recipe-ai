package com.recipe_app.recipe_app_spring.entity;

 public class RecipeResponse {
            private boolean isrecipe;
            private String recipename;
            private String dishdescription;


    public boolean isIsrecipe() {
        return this.isrecipe;
    }

    public boolean getIsrecipe() {
        return this.isrecipe;
    }

    public void setIsrecipe(boolean isrecipe) {
        this.isrecipe = isrecipe;
    }

    public String getRecipename() {
        return this.recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getDishdescription() {
        return this.dishdescription;
    }

    public void setDishdescription(String dishdescription) {
        this.dishdescription = dishdescription;
    }

           
        }
