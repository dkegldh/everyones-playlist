package com.jin.everyonesplaylist.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
public class BaseTimeEntity extends BaseEntity {

  @LastModifiedDate
  @Column(nullable = false)
  private Instant updateAt;
}
