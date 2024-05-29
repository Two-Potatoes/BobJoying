package com.twoPotatoes.bobJoying.ingredient.service;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientUpdateRequestDto;

public interface MyIngredientService {
    /**
     * 필요한 정보를 받아 정보에 맞는 식재료를 등록합니다.
     *
     * @param userDetails   인증된 사용자 정보
     * @param requestDto    식재료 등록에 필요한 정보
     * @return 식재료 등록 성공 메시지
     */
    ApiResponseDto createMyIngredient(UserDetailsImpl userDetails, MyIngredientCreateRequestDto requestDto);

    /**
     * 필요한 정보를 받아 정보에 맞게 식재료를 수정합니다.
     *
     * @param userDetails 인증된 사용자 정보
     * @param requestDto 식재료 수정에 필요한 정보
     * @return 식재료 수정 성공 메시지
     */
    MyIngredientResponseDto updateMyIngredient(UserDetailsImpl userDetails, MyIngredientUpdateRequestDto requestDto);
}
