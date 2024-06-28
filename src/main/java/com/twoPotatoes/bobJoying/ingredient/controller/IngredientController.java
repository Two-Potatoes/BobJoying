package com.twoPotatoes.bobJoying.ingredient.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.service.IngredientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ApiResponseDto createIngredient(
        @Argument @Valid IngredientCreateRequestDto ingredientCreateRequestDto) {
        return ingredientService.createIngredient(ingredientCreateRequestDto);
    }
    // todo : service 구현
    // todo : hasrole test
    // todo : graphql schema
    // todo : service test code
    // todo : controller test code
}
