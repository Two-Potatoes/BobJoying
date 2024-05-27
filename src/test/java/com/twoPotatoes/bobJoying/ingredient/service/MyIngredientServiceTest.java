package com.twoPotatoes.bobJoying.ingredient.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;
import com.twoPotatoes.bobJoying.ingredient.repository.IngredientRepository;
import com.twoPotatoes.bobJoying.ingredient.repository.MyIngredientRepository;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.MemberRoleEnum;

@ExtendWith(MockitoExtension.class)
class MyIngredientServiceTest {
    @Mock
    MyIngredientRepository myIngredientRepository;
    @Mock
    IngredientRepository ingredientRepository;
    @InjectMocks
    MyIngredientServiceImpl myIngredientService;
    MyIngredientCreateRequestDto createRequestDto;
    UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        createRequestDto = MyIngredientCreateRequestDto.builder()
            .ingredientId(1)
            .quantity(1)
            .unit("개")
            .storageDate(LocalDate.of(2024, 5, 7))
            .expirationDate(LocalDate.of(2024, 5, 27))
            .storage(StorageEnum.FRIDGE)
            .build();
        Member member = Member.builder().id(1).build();
        userDetails = new UserDetailsImpl(member);
    }

    @Test
    @DisplayName("createMyIngredient - quantity는 소수점 첫째 자리까지 허용한다.")
    void createMyIngredientFailByQuantity() {
        // given
        createRequestDto.setQuantity(1.25f);

        // when
        assertThrows(
            CustomException.class,
            () -> myIngredientService.createMyIngredient(userDetails, createRequestDto)
        );
    }

    @Test
    @DisplayName("createMyIngredient - 식재료 등록 날짜는 소비기한을 넘길 수 없다.")
    void createMyIngredientFailByExpirationDate() {
        // given
        createRequestDto.setStorageDate(LocalDate.of(2024, 5, 27));
        createRequestDto.setExpirationDate(LocalDate.of(2024, 5, 20));

        // when
        assertThrows(
            CustomException.class,
            () -> myIngredientService.createMyIngredient(userDetails, createRequestDto)
        );
    }

    @Test
    @DisplayName("createMyIngredient - 요청정보의 재료 ID에 맞는 Ingredient가 존재하지 않을 경우")
    void createMyIngredientFailByIngredient() {
        // given
        given(ingredientRepository.findById(anyInt())).willReturn(Optional.empty());

        // when
        assertThrows(
            CustomException.class,
            () -> myIngredientService.createMyIngredient(userDetails, createRequestDto)
        );
    }

    @Test
    @DisplayName("createMyIngredient 성공")
    void createMyIngredientSuccess() {
        // given
        Ingredient ingredient = new Ingredient();
        given(ingredientRepository.findById(anyInt())).willReturn(Optional.of(ingredient));

        // when
        myIngredientService.createMyIngredient(userDetails, createRequestDto);

        // then
        then(myIngredientRepository).should().save(any(MyIngredient.class));
        then(myIngredientRepository).shouldHaveNoMoreInteractions();
    }

}
