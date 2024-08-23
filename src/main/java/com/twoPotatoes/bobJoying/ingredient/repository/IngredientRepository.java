package com.twoPotatoes.bobJoying.ingredient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>, IngredientRepositoryCustom {
    Optional<Ingredient> findByName(String name);
}
