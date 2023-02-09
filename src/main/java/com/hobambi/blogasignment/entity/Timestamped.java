package com.hobambi.blogasignment.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 작성 시간과 수정 시간을 넣어줍시다
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {
    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
