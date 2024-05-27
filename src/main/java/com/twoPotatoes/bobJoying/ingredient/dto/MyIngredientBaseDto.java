package com.twoPotatoes.bobJoying.ingredient.dto;

import java.time.LocalDate;

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
    private float quantity;
    @Size(max = 10, message = "단위는 10자를 넘어갈 수 없습니다.")
    private String unit;
    private LocalDate storageDate;
    private LocalDate expirationDate;
    private StorageEnum storage;
}
