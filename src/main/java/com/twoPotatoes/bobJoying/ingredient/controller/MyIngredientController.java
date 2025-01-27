package com.twoPotatoes.bobJoying.ingredient.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.dto.PageRequestDto;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientPageRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.service.MyIngredientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyIngredientController {
    private final MyIngredientService myIngredientService;

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponseDto createMyIngredient(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Argument @Valid MyIngredientCreateRequestDto myIngredientCreateRequestDto) {
        return myIngredientService.createMyIngredient(userDetails, myIngredientCreateRequestDto);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public MyIngredientResponseDto updateMyIngredient(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Argument @Valid MyIngredientUpdateRequestDto myIngredientUpdateRequestDto) {
        return myIngredientService.updateMyIngredient(userDetails, myIngredientUpdateRequestDto);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponseDto deleteMyIngredient(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Argument int myIngredientId) {
        return myIngredientService.deleteMyIngredient(userDetails, myIngredientId);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public MyIngredientResponseDto getMyIngredient(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Argument int myIngredientId) {
        return myIngredientService.getMyIngredient(userDetails, myIngredientId);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<MyIngredientResponseDto> getMyIngredientsByCategory(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Argument @Valid MyIngredientPageRequestDto myIngredientPageRequestDto) {
        return myIngredientService.getMyIngredientsByCategory(userDetails, myIngredientPageRequestDto);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<MyIngredientResponseDto> getMyIngredientsByStorage(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Argument @Valid MyIngredientPageRequestDto myIngredientPageRequestDto) {
        return myIngredientService.getMyIngredientsByStorage(userDetails, myIngredientPageRequestDto);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<MyIngredientResponseDto> getMyIngredients(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Argument @Valid PageRequestDto pageRequestDto) {
        return myIngredientService.getMyIngredients(userDetails, pageRequestDto);
    }

}
