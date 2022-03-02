package com.kms.project.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들도 컬럼으로 인식하도록 함
@MappedSuperclass
// BaseTimeEntity 클래스에 Auditing 기능을 포함

@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    // Entity가 생성되어 저장될 때 시간이 자동으로 저장
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 Entity의 값을 변경할 때 시간이 자동 저장
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
