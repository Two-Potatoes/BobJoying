package com.twoPotatoes.bobJoying.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;

public interface MyIngredientRepository extends JpaRepository<MyIngredient, Integer> {
}
