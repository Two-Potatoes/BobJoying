package com.twoPotatoes.bobJoying.ingredient.dto;

import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IngredientResponseDto {
    private Integer id;
    private CategoryEnum category;
    private String name;
    private StorageEnum storage;
    private String unit;
}
