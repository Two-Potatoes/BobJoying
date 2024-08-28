package com.twoPotatoes.bobJoying.ingredient.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;
import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientPageRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Override
    public ApiResponseDto createIngredient(IngredientCreateRequestDto requestDto) {
        checkIngredientExists(requestDto.getName());
        Ingredient ingredient = requestDto.toEntity();
        ingredientRepository.save(ingredient);
        return new ApiResponseDto(MyIngredientConstants.CREATE_MY_INGREDIENT_SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDto getIngredient(int ingredientId) {
        return findIngredient(ingredientId).toIngredientResponseDto();
    }

    @Override
    public IngredientResponseDto updateIngredient(IngredientUpdateRequestDto requestDto) {
        Ingredient ingredient = findIngredient(requestDto.getId());
        ingredient.update(requestDto);
        return ingredient.toIngredientResponseDto();
    }

    @Override
    public ApiResponseDto deleteIngredient(int ingredientId) {
        findIngredient(ingredientId);
        ingredientRepository.deleteById(ingredientId);
        return new ApiResponseDto(MyIngredientConstants.DELETE_MY_INGREDIENT_SUCCESS);
    }

    @Override
    public List<IngredientResponseDto> getIngredientsByCategory(MyIngredientPageRequestDto requestDto) {
        requestDto.setPage(requestDto.getPage() - 1);
        List<Ingredient> ingredientList = ingredientRepository.findAllByCategory(
            requestDto.toPageRequestDto(),
            requestDto.getCategoryEnum()
        );
        if (ingredientList.isEmpty()) {
            throw new CustomException(CustomErrorCode.NO_MORE_DATA);
        }
        return ingredientList.stream().map(Ingredient::toIngredientResponseDto).toList();
    }

    public Ingredient findIngredient(int id) {
        return ingredientRepository.findById(id).orElseThrow(
            () -> new CustomException(CustomErrorCode.INGREDIENT_NOT_FOUND)
        );
    }

    private void checkIngredientExists(String name) {
        if (ingredientRepository.findByName(name).isPresent()) {
            throw new CustomException(CustomErrorCode.INGREDIENT_ALREADY_EXISTS);
        }
    }
}
