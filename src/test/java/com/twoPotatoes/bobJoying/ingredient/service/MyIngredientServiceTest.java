package com.twoPotatoes.bobJoying.ingredient.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.twoPotatoes.bobJoying.common.constants.ErrorMsgConstants;
import com.twoPotatoes.bobJoying.common.constants.MyIngredientConstants;
import com.twoPotatoes.bobJoying.common.exception.CustomException;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.entity.Ingredient;
import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;
import com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum;
import com.twoPotatoes.bobJoying.ingredient.repository.IngredientRepository;
import com.twoPotatoes.bobJoying.ingredient.repository.MyIngredientRepository;
import com.twoPotatoes.bobJoying.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class MyIngredientServiceTest {
    @Mock
    MyIngredientRepository myIngredientRepository;
    @Mock
    IngredientRepository ingredientRepository;
    @InjectMocks
    MyIngredientServiceImpl myIngredientService;
    MyIngredientCreateRequestDto createRequestDto;
    MyIngredientUpdateRequestDto updateRequestDto;
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
        updateRequestDto = MyIngredientUpdateRequestDto.builder()
            .myIngredientId(1)
            .quantity(1)
            .unit("조각")
            .storageDate(LocalDate.of(2024, 5, 8))
            .expirationDate(LocalDate.of(2024, 5, 28))
            .storage(StorageEnum.ROOM_TEMPERATURE)
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
    @DisplayName("createMyIngredient - 소비 기한은 저장 날짜를 포함하여 그 이후의 날짜여야 한다.")
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
    @DisplayName("createMyIngredient - 저장 날짜가 없을 경우, 소비 기한은 오늘을 포함하여 그 이후의 날짜여야 한다.")
    void createMyIngredientFailByExpirationDate2() {
        // given
        createRequestDto.setStorageDate(null);
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        createRequestDto.setExpirationDate(yesterday);

        // when
        assertThrows(
            CustomException.class,
            () -> myIngredientService.createMyIngredient(userDetails, createRequestDto)
        );
    }

    @Test
    @DisplayName("createMyIngredient - 소비 기한은 현재 날짜로부터 50년을 초과할 수 없다.")
    void createMyIngredientFailByExpirationDateTooLong() {
        // given
        createRequestDto.setStorageDate(null);
        LocalDate now = LocalDate.now();
        LocalDate tooLongExpirationDate = now.plusYears(52);
        createRequestDto.setExpirationDate(tooLongExpirationDate);

        // when
        assertThrows(
            CustomException.class,
            () -> myIngredientService.createMyIngredient(userDetails, createRequestDto)
        );
    }

    @Test
    @DisplayName("createMyIngredient - 요청정보의 재료 ID에 맞는 Ingredient가 존재하지 않을 경우")
    void createMyIngredientFailByIngredientNotFound() {
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

    @Test
    @DisplayName("updateMyIngredient - 수정하려는 식재료 ID의 MyIngredient가 존재하지 않을 경우")
    void updateMyIngredientFailByMyIngredientNotFound() {
        // given
        given(myIngredientRepository.findById(anyInt())).willReturn(Optional.empty());

        // when
        assertThrows(
            CustomException.class,
            () -> myIngredientService.updateMyIngredient(userDetails, updateRequestDto)
        );
    }

    @Test
    @DisplayName("updateMyIngredient - 유저는 유저가 등록한 재료만 수정할 수 있다.")
    void updateMyIngredientFailByWrongMember() {
        // given
        Member member = Member.builder().id(2).build();
        MyIngredient myIngredient = MyIngredient.builder().member(member).build();
        given(myIngredientRepository.findById(anyInt())).willReturn(Optional.of(myIngredient));

        // when
        assertThrows(
            CustomException.class,
            () -> myIngredientService.updateMyIngredient(userDetails, updateRequestDto)
        );
    }

    @Test
    @DisplayName("updateMyIngredient 성공")
    void updateMyIngredientSuccess() {
        // given
        Member member = Member.builder().id(1).build();
        MyIngredient myIngredient = MyIngredient.builder().id(1).member(member).build();
        given(myIngredientRepository.findById(anyInt())).willReturn(Optional.of(myIngredient));

        // when
        MyIngredientResponseDto responseDto = myIngredientService.updateMyIngredient(userDetails, updateRequestDto);

        // then
        assertEquals(updateRequestDto.getQuantity(), responseDto.getQuantity());
        assertEquals(updateRequestDto.getUnit(), responseDto.getUnit());
        assertEquals(updateRequestDto.getStorageDate(), responseDto.getStorageDate());
        assertEquals(updateRequestDto.getStorage(), responseDto.getStorage());
        assertEquals(updateRequestDto.getMyIngredientId(), responseDto.getMyIngredientId());
        int dday = (int)ChronoUnit.DAYS.between(LocalDate.now(), updateRequestDto.getExpirationDate());
        assertEquals(dday, responseDto.getDDay());
    }
}
