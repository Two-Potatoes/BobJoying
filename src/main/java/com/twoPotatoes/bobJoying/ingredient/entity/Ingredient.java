package com.twoPotatoes.bobJoying.ingredient.entity;

import java.util.ArrayList;
import java.util.List;

import com.twoPotatoes.bobJoying.ingredient.dto.IngredientResponseDto;
import com.twoPotatoes.bobJoying.recipe.entity.RecipeIngredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum category;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StorageEnum storage;

    @Column(nullable = false, length = 10)
    private String unit;

    // cascade 옵션을 걸어주지 않습니다. 잘못하다가 유저들의 정보가 삭제될 수도 있기 때문입니다.
    @OneToMany(mappedBy = "ingredient")
    @Builder.Default
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

    @OneToMany(mappedBy = "ingredient")
    @Builder.Default
    private List<MyIngredient> myIngredientList = new ArrayList<>();

    public IngredientResponseDto toIngredientResponseDto() {
        return IngredientResponseDto.builder()
            .id(id)
            .category(category)
            .name(name)
            .storage(storage)
            .unit(unit)
            .build();
    }
}
