package kamenov.springkamenovnatnature.repositories;

import kamenov.springkamenovnatnature.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByTitleContaining(String title);
}
