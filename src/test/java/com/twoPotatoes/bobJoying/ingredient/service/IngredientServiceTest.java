package com.twoPotatoes.bobJoying.ingredient.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;
import com.twoPotatoes.bobJoying.ingredient.repository.IngredientRepository;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {
    @Mock
    IngredientRepository ingredientRepository;
    @InjectMocks
    IngredientServiceImpl ingredientService;
    IngredientCreateRequestDto createRequestDto;

    @BeforeEach
    void setUp() {
        createRequestDto = IngredientCreateRequestDto.builder()
            .category(CategoryEnum.DAIRY_PRODUCT)
            .name("우유")
            .storage(StorageEnum.FRIDGE)
            .unit("ml")
            .build();
    }

    @Test
    @DisplayName("createIngredient 실패 - 같은 이름의 식재료가 이미 존재할 경우")
    void createIngredientFailByIngredientAlreadyExists() {
        // given
        given(ingredientRepository.findByName(anyString())).willReturn(Optional.of(new Ingredient()));

        // when
        assertThrows(
            CustomException.class,
            () -> ingredientService.createIngredient(createRequestDto)
        );
    }

    @Test
    @DisplayName("createIngredient 성공")
    void createIngredient() {
        // given
        given(ingredientRepository.findByName(anyString())).willReturn(Optional.empty());

        // when
        ingredientService.createIngredient(createRequestDto);

        // then
        then(ingredientRepository).should().save(any(Ingredient.class));
        then(ingredientRepository).shouldHaveNoMoreInteractions();
    }
}
