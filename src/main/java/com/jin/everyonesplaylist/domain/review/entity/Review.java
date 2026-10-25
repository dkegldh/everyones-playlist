package com.jin.everyonesplaylist.domain.review.entity;

import com.jin.everyonesplaylist.domain.content.entity.Content;
import com.jin.everyonesplaylist.domain.user.entity.User;
import com.jin.everyonesplaylist.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "content_id", nullable = false)
  private Content content;

  @Column(name = "text", columnDefinition = "TEXT")
  private String text;

  @Column(nullable = false)
  private double rating;

  private Review(User author, Content content, String text, double rating) {
    this.content = content;
    this.author = author;
    this.text = text;
    this.rating = rating;
  }

  public static Review create(User author, Content content, String text, double rating) {
    return new Review(author, content, text, rating);
  }

  public void updateText(String text) {
    this.text = text;
  }

  public void updateRating(double rating) {
    this.rating = rating;
  }
}
