package com.twoPotatoes.bobJoying.ingredient.dto;

import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MyIngredientUpdateRequestDto extends MyIngredientBaseDto {
    @Min(value = 1, message = MyIngredientConstants.INVALID_ID)
    private int myIngredientId;
}
