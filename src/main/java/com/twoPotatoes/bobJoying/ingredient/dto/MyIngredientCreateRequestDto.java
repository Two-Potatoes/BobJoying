package com.twoPotatoes.bobJoying.ingredient.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MyIngredientCreateRequestDto extends MyIngredientBaseDto {
    @Min(value = 1, message = "id는 1이상이어야 합니다.")
    private int ingredientId;
}
