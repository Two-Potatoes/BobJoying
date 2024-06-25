package com.twoPotatoes.bobJoying.ingredient.dto;

import java.time.LocalDate;

import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class MyIngredientBaseDto {
    private Float quantity;
    @Size(max = 10, message = MyIngredientConstants.INVALID_UNIT)
    private String unit;
    private LocalDate storageDate;
    private LocalDate expirationDate;
    private StorageEnum storage;
}
