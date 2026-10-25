package com.jin.everyonesplaylist.domain.playlist.entity;

import com.jin.everyonesplaylist.domain.user.entity.User;
import com.jin.everyonesplaylist.global.entity.BaseTimeEntity;
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
@Table(name = "playlists")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist extends BaseTimeEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private long subscriberCount = 0L;

  private Playlist(User owner, String title, String description) {
    this.owner = owner;
    this.title = title;
    this.description = description;
  }

  public static Playlist create(User owner, String title, String description) {
    return new Playlist(owner, title, description);
  }
}
