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
}
