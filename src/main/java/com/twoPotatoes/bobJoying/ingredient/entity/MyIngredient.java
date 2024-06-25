package com.twoPotatoes.bobJoying.ingredient.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientResponseDto;
import com.twoPotatoes.bobJoying.ingredient.dto.MyIngredientUpdateRequestDto;
import com.twoPotatoes.bobJoying.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false)
    private Float quantity;

    @Column(nullable = false, length = 10)
    private String unit;

    private LocalDate storageDate;

    private LocalDate expirationDate;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 100)
    private StorageEnum storage;

    public void update(MyIngredientUpdateRequestDto requestDto) {
        this.quantity = requestDto.getQuantity();
        this.unit = requestDto.getUnit();
        this.storageDate = requestDto.getStorageDate();
        this.expirationDate = requestDto.getExpirationDate();
        this.storage = requestDto.getStorage();
    }

    public MyIngredientResponseDto toDto() {
        Integer dday = null;
        if (expirationDate != null) {
            dday = (int)ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
        }
        return MyIngredientResponseDto.builder()
            .myIngredientId(id)
            .dDay(dday)
            .name(ingredient.getName())
            .quantity(quantity)
            .unit(unit)
            .storageDate(storageDate)
            .expirationDate(expirationDate)
            .storage(storage)
            .build();
    }
}
