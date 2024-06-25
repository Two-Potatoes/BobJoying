package com.twoPotatoes.bobJoying.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;

@Repository
public interface MyIngredientRepository extends JpaRepository<MyIngredient, Integer>, MyIngredientRepositoryCustom {
}
