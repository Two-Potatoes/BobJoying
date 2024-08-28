package com.twoPotatoes.bobJoying.ingredient.repository;

import java.util.List;

import com.twoPotatoes.bobJoying.common.dto.PageRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

public interface IngredientRepositoryCustom {
    // 식재료 Category 별 조회
    List<Ingredient> findAllByCategory(
        PageRequestDto pageDto, CategoryEnum category
    );

    // 식재료 Storage 별 조회
    List<Ingredient> findAllByStorage(
        PageRequestDto pageDto, StorageEnum storage
    );

    // 식재료 모두 조회
    List<Ingredient> findAll(
        PageRequestDto pageDto
    );
}
