package kamenov.springkamenovnatnature.web;

import kamenov.springkamenovnatnature.entity.Recipe;
import kamenov.springkamenovnatnature.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:3001")
public class RecipeController {
    @Autowired
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestParam String title) {
        return service.searchRecipes(title);
    }
}
