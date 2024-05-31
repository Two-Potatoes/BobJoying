package com.twoPotatoes.bobJoying.common.entity;

import java.time.ZonedDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

@Getter
// 공통 맵핑 정보가 필요할 때 사용합니다. Timestamped를 상속받는 클래스는 모두 createdAt 필드가 있어야 합니다.
@MappedSuperclass
// Entity에 이벤트가 발생할 때 마다 관련 코드를 실행
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    @Column(updatable = false)
    private ZonedDateTime createdAt;

    @PrePersist                          // Entity가 INSERT 되기 전에 원하는 메서드 실행
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
    }
}
