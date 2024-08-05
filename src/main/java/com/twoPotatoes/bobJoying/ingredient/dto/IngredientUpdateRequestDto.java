package com.twoPotatoes.bobJoying.ingredient.dto;

import com.twoPotatoes.bobJoying.common.constants.CommonValidationConstants;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientUpdateRequestDto {
    @Min(value = 1, message = CommonValidationConstants.ONLY_POSITIVE_ALLOWED)
    private Integer id;
    private CategoryEnum category;
    @NotBlank(message = CommonValidationConstants.BLANK_NOT_ALLOWED)
    private String name;
    private StorageEnum storage;
    @NotBlank(message = CommonValidationConstants.BLANK_NOT_ALLOWED)
    private String unit;
}
