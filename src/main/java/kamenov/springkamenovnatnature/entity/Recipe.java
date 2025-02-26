package kamenov.springkamenovnatnature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {
    @Column(name = "title",nullable = false,unique = true)
    private String title;
    @Column(nullable = false)
    private String ingredients;
    @Column(columnDefinition = "TEXT")
    private String instructions;

    public Recipe() {
    }

    public String getTitle() {
        return title;
    }

    public Recipe setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public Recipe setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }
}
