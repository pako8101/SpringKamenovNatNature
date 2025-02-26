package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.Recipe;
import kamenov.springkamenovnatnature.repositories.RecipeRepository;
import kamenov.springkamenovnatnature.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private final RecipeRepository repository;

    public RecipeServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }
@Override
    public List<Recipe> searchRecipes(String title) {
        return repository.findByTitleContaining(title);
    }
}
