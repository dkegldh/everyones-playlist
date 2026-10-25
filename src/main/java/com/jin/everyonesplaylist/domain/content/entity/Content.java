package com.jin.everyonesplaylist.domain.content.entity;

import com.jin.everyonesplaylist.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseTimeEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private ContentType type;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "thumbnailUrl")
  private String thumbnailUrl;

  @Column(name = "average_sum", nullable = false)
  private double averageRating = 0.0;

  @Column(name = "review_count", nullable = false)
  private int reviewCount = 0;

  @Column(name = "watcher_count", nullable = false)
  private long watcherCount = 0L;

  private Content(ContentType type, String title, String description, String thumbnailUrl) {
    this.type = type;
    this.title = title;
    this.description = description;
    this.thumbnailUrl = thumbnailUrl;
  }

  public void updateRatingStats(Double averageRating, Integer reviewCount) {
    this.averageRating = averageRating;
    this.reviewCount = reviewCount;
  }

  public void updateContentInfo(String title, String description, String thumbnailUrl) {
    this.title = title;
    this.description = description;
    this.thumbnailUrl = thumbnailUrl;
  }
}
