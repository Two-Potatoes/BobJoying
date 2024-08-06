package com.twoPotatoes.bobJoying.ingredient.service;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientUpdateRequestDto;

public interface IngredientService {
    /**
     * 필요한 정보를 받아 정보에 맞는 식재료를 등록합니다.
     *
     * @param requestDto 식재료 등록에 필요한 정보
     * @return 식재료 등록 성공 메시지
     */
    ApiResponseDto createIngredient(IngredientCreateRequestDto requestDto);

    /**
     * ID에 맞는 식재료를 조회합니다.
     *
     * @param ingredientId 조회할 식재료 ID
     * @return 식재료 정보
     */
    IngredientResponseDto getIngredient(int ingredientId);

    /**
     * ID와 수정할 정보를 받아 ID에 맞는 식재료를 조회해서 정보에 맞게 수정합니다.
     *
     * @param requestDto 수정할 식재료 ID와 식재료 수정에 필요한 정보
     * @return 식재료 수정 정보
     */
    IngredientResponseDto updateIngredient(IngredientUpdateRequestDto requestDto);

    /**
     * 삭제할 식재료 ID를 받아 ID에 맞는 식재료를 삭제합니다.
     * @param ingredientId 삭제할 식재료 ID
     * @return 식재료 삭제 성공 메시지
     */
    ApiResponseDto deleteIngredient(int ingredientId);
}
