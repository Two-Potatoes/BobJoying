package com.twoPotatoes.bobJoying.ingredient.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;
import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.exception.CustomErrorCode;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;
import com.twoPotatoes.bobJoying.ingredient.repository.IngredientRepository;
import com.twoPotatoes.bobJoying.ingredient.repository.MyIngredientRepository;
import com.twoPotatoes.bobJoying.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyIngredientServiceImpl implements MyIngredientService {
    private final MyIngredientRepository myIngredientRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public ApiResponseDto createMyIngredient(UserDetailsImpl userDetails, MyIngredientCreateRequestDto requestDto) {
        validateRequest(requestDto.getQuantity(), requestDto.getExpirationDate(), requestDto.getStorageDate());
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

    @Override
    public MyIngredientResponseDto updateMyIngredient(
        UserDetailsImpl userDetails, MyIngredientUpdateRequestDto requestDto) {
        validateRequest(requestDto.getQuantity(), requestDto.getExpirationDate(), requestDto.getStorageDate());
        MyIngredient target = findMyIngredient(requestDto.getMyIngredientId());
        checkAuthority(userDetails, target.getMember());
        target.update(requestDto);
        return target.toDto();
    }

    @Override
    public ApiResponseDto deleteMyIngredient(UserDetailsImpl userDetails, int myIngredientId) {
        MyIngredient target = findMyIngredient(myIngredientId);
        checkAuthority(userDetails, target.getMember());
        myIngredientRepository.deleteById(myIngredientId);
        return new ApiResponseDto(MyIngredientConstants.DELETE_MY_INGREDIENT_SUCCESS);
    }

    @Override
    public MyIngredientResponseDto getMyIngredient(UserDetailsImpl userDetails, int myIngredientId) {
        MyIngredient target = findMyIngredient(myIngredientId);
        checkAuthority(userDetails, target.getMember());
        return target.toDto();
    }

    private static void checkAuthority(UserDetailsImpl userDetails, Member member) {
        if (!member.getId().equals(userDetails.getMember().getId())) {
            throw new CustomException(CustomErrorCode.INVALID_ACCESS);
        }
    }

    private void validateRequest(float quantity, LocalDate expirationDate, LocalDate storageDate) {
        // quantity는 소수점 첫째 자리까지 허용한다.
        if ((quantity * 10) % 1 != 0) {
            throw new CustomException(CustomErrorCode.INVALID_QUANTITY);
        }

        // 소비기한과 등록 날짜가 null이 아닌 경우 소비기한은 등록 날짜 이후여야 한다.
        if (expirationDate != null && storageDate != null) {
            if (expirationDate.isBefore(storageDate)) {
                throw new CustomException(CustomErrorCode.INVALID_EXPIRATION_DATE);
            }
        } else if (expirationDate != null) {
            // 등록 날짜가 null인 경우 소비기한은 오늘을 포함하여 그 이후의 날짜여야 한다.
            if (expirationDate.isBefore(LocalDate.now())) {
                throw new CustomException(CustomErrorCode.INVALID_EXPIRATION_DATE);
            }

            // 소비기한은 허용된 범위를 초과할 수 없다.
            if (expirationDate.isAfter(LocalDate.now().plusYears(50))) {
                throw new CustomException(CustomErrorCode.TOO_LONG_EXPIRATION_DATE);
            }
        }
    }

    private Ingredient findIngredient(int id) {
        return ingredientRepository.findById(id).orElseThrow(
            () -> new CustomException(CustomErrorCode.INGREDIENT_NOT_FOUND)
        );
    }

    private MyIngredient findMyIngredient(int id) {
        return myIngredientRepository.findById(id).orElseThrow(
            () -> new CustomException(CustomErrorCode.MY_INGREDIENT_NOT_FOUND)
        );
    }
}
