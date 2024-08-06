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

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientUpdateRequestDto;
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

    @Test
    @DisplayName("getIngredient 실패 - id에 해당하는 식재료가 존재하지 않을 경우")
    void getIngredientFailByIngredientNotFound() {
        // given
        given(ingredientRepository.findById(anyInt())).willReturn(Optional.empty());

        // when
        assertThrows(
            CustomException.class,
            () -> ingredientService.getIngredient(2)
        );
    }

    @Test
    @DisplayName("getIngredient 성공")
    void getIngredient() {
        // given
        Ingredient ingredient = Ingredient.builder()
            .id(2)
            .name("사과")
            .category(CategoryEnum.FRUIT)
            .unit("개")
            .storage(StorageEnum.FRIDGE).build();
        given(ingredientRepository.findById(anyInt())).willReturn(Optional.of(ingredient));

        // when
        IngredientResponseDto responseDto = ingredientService.getIngredient(2);

        // then
        assertEquals("사과", responseDto.getName());
    }

    @Test
    @DisplayName("updateIngredient 성공")
    void updateIngredient() {
        // given
        String updateName = "사과";
        CategoryEnum updateCategory = CategoryEnum.FRUIT;
        StorageEnum updateStorage = StorageEnum.FRIDGE;
        String updateUnit = "개";

        IngredientUpdateRequestDto requestDto =
            IngredientUpdateRequestDto.builder()
                .id(3)
                .category(updateCategory)
                .name(updateName)
                .storage(updateStorage)
                .unit(updateUnit)
                .build();
        given(ingredientRepository.findById(anyInt())).willReturn(Optional.of(new Ingredient()));

        // when
        IngredientResponseDto responseDto = ingredientService.updateIngredient(requestDto);

        // then
        assertEquals(updateName, responseDto.getName());
        assertEquals(updateCategory, responseDto.getCategory());
        assertEquals(updateStorage, responseDto.getStorage());
        assertEquals(updateUnit, responseDto.getUnit());
    }

    @Test
    @DisplayName("deleteIngredient 성공")
    void deleteIngredient() {
        // given
        given(ingredientRepository.findById(anyInt())).willReturn(Optional.of(new Ingredient()));

        // when
        ingredientService.deleteIngredient(3);

        // then
        then(ingredientRepository).should().findById(anyInt());
        then(ingredientRepository).should().deleteById(anyInt());
        then(ingredientRepository).shouldHaveNoMoreInteractions();
    }
}
