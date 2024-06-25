package com.twoPotatoes.bobJoying.ingredient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class MyIngredientResponseDto extends MyIngredientBaseDto {
    private String name;
    private Integer myIngredientId;
    private Integer dDay;
}
