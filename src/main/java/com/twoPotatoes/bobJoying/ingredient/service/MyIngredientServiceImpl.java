package com.twoPotatoes.bobJoying.ingredient.service;

import org.springframework.stereotype.Service;

import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;
import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;
import com.twoPotatoes.bobJoying.ingredient.repository.IngredientRepository;
import com.twoPotatoes.bobJoying.ingredient.repository.MyIngredientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyIngredientServiceImpl implements MyIngredientService {
    private final MyIngredientRepository myIngredientRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public ApiResponseDto createMyIngredient(UserDetailsImpl userDetails, MyIngredientCreateRequestDto requestDto) {
        validateRequest(requestDto);
        Ingredient ingredient = findIngredient(requestDto.getIngredientId());
        MyIngredient myIngredient = MyIngredient.builder()
            .member(userDetails.getMember())
            .ingredient(ingredient)
            .quantity(requestDto.getQuantity())
            .unit(requestDto.getUnit())
            .storageDate(requestDto.getStorageDate())
            .expirationDate(requestDto.getExpirationDate())
            .storage(requestDto.getStorage())
            .build();
        myIngredientRepository.save(myIngredient);
        return new ApiResponseDto(MyIngredientConstants.CREATE_MY_INGREDIENT_SUCCESS);
    }

    private void validateRequest(MyIngredientCreateRequestDto requestDto) {
        // quantity는 소수점 첫째 자리까지 허용한다.
        if ((requestDto.getQuantity() * 10) % 1 != 0) {
            throw new CustomException(CustomErrorCode.INVALID_QUANTITY);
        }
        // 소비기한이 null이 아닌 경우 식재료 등록 날짜는 소비기한을 넘길 수 없다.
        if (requestDto.getExpirationDate() != null) {
            if (requestDto.getExpirationDate().isBefore(requestDto.getStorageDate())) {
                throw new CustomException(CustomErrorCode.INVALID_EXPIRATION_DATE);
            }
        }
    }

    private Ingredient findIngredient(int id) {
        return ingredientRepository.findById(id).orElseThrow(
            () -> new CustomException(CustomErrorCode.INGREDIENT_NOT_FOUND)
        );
    }
}
