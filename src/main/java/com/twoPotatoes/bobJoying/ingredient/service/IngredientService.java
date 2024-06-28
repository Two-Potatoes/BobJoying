package com.twoPotatoes.bobJoying.ingredient.service;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.IngredientCreateRequestDto;

public interface IngredientService {
    /**
     * 필요한 정보를 받아 정보에 맞는 식재료를 등록합니다.
     *
     * @param requestDto 식재료 등록에 필요한 정보
     * @return 식재료 등록 성공 메시지
     */
    ApiResponseDto createIngredient(IngredientCreateRequestDto requestDto);
}
