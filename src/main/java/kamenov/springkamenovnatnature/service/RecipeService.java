package kamenov.springkamenovnatnature.service;

import kamenov.springkamenovnatnature.entity.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> searchRecipes(String title);
}
