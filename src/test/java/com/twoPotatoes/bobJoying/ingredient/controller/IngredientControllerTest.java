package com.twoPotatoes.bobJoying.ingredient.controller;

import static com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum.*;
import static com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum.*;
import static org.mockito.BDDMockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.twoPotatoes.bobJoying.common.config.GraphQlConfig;
import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.CategoryEnum;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;
import com.twoPotatoes.bobJoying.ingredient.service.IngredientService;

@GraphQlTest(IngredientController.class)
@Import(GraphQlConfig.class)
class IngredientControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;
    @MockBean
    private IngredientService ingredientService;
    ApiResponseDto apiResponseDto;

    @BeforeEach
    void setUp() {
        apiResponseDto = new ApiResponseDto("요청 성공!");
    }

    @Test
    @DisplayName("createIngredient")
    void createIngredient() {
        // given
        Map<String, Object> requestDto = new HashMap<>();
        requestDto.put("category", DAIRY_PRODUCT);
        requestDto.put("name", "우유");
        requestDto.put("storage", FRIDGE);
        requestDto.put("unit", "ml");

        given(ingredientService.createIngredient(any(IngredientCreateRequestDto.class)))
            .willReturn(apiResponseDto);

        // when
        graphQlTester.documentName("ingredient")
            .variable("input", requestDto)
            .operationName("createIngredient")
            .execute()
            .path("createIngredient.message")
            .entity(String.class);
    }

    @Test
    @DisplayName("getIngredient")
    void getIngredient() {
        IngredientResponseDto responseDto = IngredientResponseDto.builder().build();
        given(ingredientService.getIngredient(anyInt())).willReturn(responseDto);

        // when
        graphQlTester.documentName("ingredient")
            .variable("input", 2)
            .operationName("getIngredient")
            .execute();
    }

    @Test
    @DisplayName("updateIngredient")
    void updateIngredient() {
        // given
        int updateId = 3;
        CategoryEnum updateCategory = MEAT;
        String updateName = "돼지고기";
        StorageEnum updateStorage = FREEZER;
        String updateUnit = "g";

        Map<String, Object> requestDto = new HashMap<>();
        requestDto.put("id", updateId);
        requestDto.put("category", updateCategory);
        requestDto.put("name", updateName);
        requestDto.put("storage", updateStorage);
        requestDto.put("unit", updateUnit);

        IngredientResponseDto responseDto = IngredientResponseDto.builder()
            .id(updateId)
            .category(updateCategory)
            .name(updateName)
            .storage(updateStorage)
            .unit(updateUnit)
            .build();

        given(ingredientService.updateIngredient(any(IngredientUpdateRequestDto.class)))
            .willReturn(responseDto);

        // when
        graphQlTester.documentName("ingredient")
            .variable("input", requestDto)
            .operationName("updateIngredient")
            .execute()
            .path("updateIngredient.name")
            .entity(String.class)
            .isEqualTo(updateName);
    }

    @Test
    @DisplayName("deleteIngredient")
    void deleteIngredient() {
        // given
        given(ingredientService.deleteIngredient(anyInt())).willReturn(apiResponseDto);

        // when
        graphQlTester.documentName("ingredient")
            .variable("input",3)
            .operationName("deleteIngredient")
            .execute()
            .path("deleteIngredient.message")
            .entity(String.class);
    }
}
