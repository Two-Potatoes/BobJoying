package com.twoPotatoes.bobJoying.ingredient.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientPageRequestDto;
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

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public IngredientResponseDto getIngredient(@Argument int ingredientId) {
        return ingredientService.getIngredient(ingredientId);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public IngredientResponseDto updateIngredient(
        @Argument @Valid IngredientUpdateRequestDto ingredientUpdateRequestDto) {
        return ingredientService.updateIngredient(ingredientUpdateRequestDto);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public ApiResponseDto deleteIngredient(@Argument int ingredientId) {
        return ingredientService.deleteIngredient(ingredientId);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<IngredientResponseDto> getIngredientsByCategory(
        @Argument @Valid MyIngredientPageRequestDto myIngredientPageRequestDto) {
        return ingredientService.getIngredientsByCategory(myIngredientPageRequestDto);
    }
}
