package com.twoPotatoes.bobJoying.ingredient.controller;

import static com.twoPotatoes.bobJoying.ingredient.entity.StorageEnum.*;
import static org.mockito.BDDMockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.twoPotatoes.bobJoying.common.config.GraphQlConfig;
import com.twoPotatoes.bobJoying.common.dto.ApiResponseDto;
import com.twoPotatoes.bobJoying.common.security.UserDetailsImpl;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientCreateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.ingredient.service.MyIngredientService;
import com.twoPotatoes.bobJoying.member.entity.Member;
import com.twoPotatoes.bobJoying.member.entity.MemberRoleEnum;

@GraphQlTest(MyIngredientController.class)
@Import(GraphQlConfig.class)
class MyIngredientControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private MyIngredientService myIngredientService;
    UserDetailsImpl userDetails;
    ApiResponseDto apiResponseDto;

    @BeforeEach
    void setUp() {
        Member member = Member.builder().role(MemberRoleEnum.MEMBER).build();
        userDetails = new UserDetailsImpl(member);
        SecurityContextHolder
            .getContext()
            .setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                )
            );
        apiResponseDto = new ApiResponseDto("요청 성공!");
    }

    @Test
    @DisplayName("MyIngredientController Test - createMyIngredient")
    void createMyIngredient() {
        // given
        Map<String, Object> createRequestDto = new HashMap<>();

        createRequestDto.put("ingredientId", 1);
        createRequestDto.put("quantity", 1.5f);
        createRequestDto.put("unit", "개");
        createRequestDto.put("storageDate", "2024-05-17");
        createRequestDto.put("expirationDate", "2024-05-27");
        createRequestDto.put("storage", FRIDGE);

        given(myIngredientService.createMyIngredient(
                any(UserDetailsImpl.class),
                any(MyIngredientCreateRequestDto.class)
            )
        ).willReturn(apiResponseDto);

        // when
        graphQlTester.documentName("myIngredient")
            .variable("input", createRequestDto)
            .operationName("createMyIngredient")
            .execute()
            .path("createMyIngredient.message")
            .entity(String.class);
    }

    @Test
    @DisplayName("MyIngredientController Test - updateMyIngredient")
    void updateMyIngredient() {
        // given
        Map<String, Object> updateRequestDto = new HashMap<>();

        updateRequestDto.put("myIngredientId", 1);
        updateRequestDto.put("quantity", 2f);
        updateRequestDto.put("unit", "개");
        updateRequestDto.put("storageDate", "2024-05-17");
        updateRequestDto.put("expirationDate", "2024-05-27");
        updateRequestDto.put("storage", FRIDGE);

        MyIngredientResponseDto myIngredientResponseDto = new MyIngredientResponseDto();
        given(myIngredientService.updateMyIngredient(
                any(UserDetailsImpl.class),
                any(MyIngredientUpdateRequestDto.class)
            )
        ).willReturn(myIngredientResponseDto);

        // when
        graphQlTester.documentName("myIngredient")
            .variable("input", updateRequestDto)
            .operationName("updateMyIngredient")
            .execute();
    }

    @Test
    @DisplayName("MyIngredientController Test - deleteMyIngredient")
    void deleteMyIngredient() {
        // given
        given(myIngredientService.deleteMyIngredient(userDetails, 1)).willReturn(apiResponseDto);

        // when
        graphQlTester.documentName("myIngredient")
            .variable("input", 1)
            .operationName("deleteMyIngredient")
            .execute()
            .path("deleteMyIngredient.message")
            .entity(String.class)
            .isEqualTo(apiResponseDto.getMessage());
    }
}
