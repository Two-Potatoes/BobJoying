package com.twoPotatoes.bobJoying.member.entity;

import java.util.ArrayList;
import java.util.List;

import com.twoPotatoes.bobJoying.common.entity.Timestamped;
import com.twoPotatoes.bobJoying.foodRequest.entity.FoodRequest;
import com.twoPotatoes.bobJoying.ingredient.entity.MyIngredient;
import com.twoPotatoes.bobJoying.mealRecord.entity.MealRecord;
import com.twoPotatoes.bobJoying.recipe.entity.Recipe;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Member 모델을 가져올 때 Team 모델은 프록시로 가져옵니다. 필요시 EAGER 옵션으로 변경합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")       // member 테이블에서 team_id로 column 명이 지정됩니다.
    private Team team;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String nickname;

    // EnumType을 String으로 DB에 저장합니다.(ex. 'USER', 'ADMIN')
    // EnumType.ORDINAL로 설정하면 EnumType의 순서를 저장합니다. (ex. 1(1번째 Enum), 2(2번째 Enum))
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MemberRoleEnum role;

    // mappedBy = "member": MyIngredient Entity에서의 Member 객체명과 맵핑시켜줍니다.
    // cascade = CascadeType.REMOVE: 부모 Entity 삭제시, 자식 Entity도 삭제됩니다.
    // orphanRemoval = true 도 같은 기능을 하지만 cascade 옵션의 경우 부모 엔티티에서 자식 엔티티를 삭제할 경우 자식 엔티티가
    // 그대로 남아있는 반면, 고아 삭제 옵션은 자식 엔티티를 제거합니다.
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    // 이 옵션을 사용하지 않으면 컬렉션 필드를 초기화하지 않을 수 있어 NullPointerException 예외가 발생할 수 있습니다.
    @Builder.Default
    private List<MyIngredient> myIngredientList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<FoodRequest> foodRequestList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Recipe> recipeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MealRecord> mealRecordList = new ArrayList<>();

}
